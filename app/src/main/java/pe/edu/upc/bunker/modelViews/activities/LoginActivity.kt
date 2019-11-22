package pe.edu.upc.bunker.modelViews.activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import pe.edu.upc.bunker.R
import pe.edu.upc.bunker.dto.LoginDTO
import pe.edu.upc.bunker.dto.LoginResponseDTO
import pe.edu.upc.bunker.dto.UserLoginDTO
import pe.edu.upc.bunker.models.Lessor
import pe.edu.upc.bunker.repository.LessorRepository
import pe.edu.upc.bunker.repository.LoginRepository
import pe.edu.upc.bunker.repository.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var userNameEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var signUpLink: TextView
    private lateinit var loginDTO: LoginDTO
    private lateinit var user: UserLoginDTO
    private lateinit var splashScreen: RelativeLayout
    private lateinit var loginFormLayout: ConstraintLayout

    private val loginRepo =
        RetrofitClientInstance().getRetrofitInstance().create(LoginRepository::class.java)
    private val lessorRepo =
        RetrofitClientInstance().getRetrofitInstance().create(LessorRepository::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userNameEditText = findViewById(R.id.user_field_edit_text)
        passwordEditText = findViewById(R.id.password_field_edit_text)
        signUpLink = findViewById(R.id.register_link)
        loginButton = findViewById(R.id.login_button)
        splashScreen = findViewById(R.id.splashScreenLogin)
        loginFormLayout = findViewById(R.id.loginFormConstraintLayout)
        pushButton()
        signUpButton()
    }

    private fun pushButton() {
        Log.d("Debug", "Login Button Pressed")
        loginButton.setOnClickListener {
            loginFormLayout.visibility = View.GONE
            splashScreen.visibility = View.VISIBLE
            Log.d("Debug", "Setting email and password to DTO")
            user = UserLoginDTO()
            loginDTO = LoginDTO()
            user.email = userNameEditText.text.toString()
            user.password = passwordEditText.text.toString()

            loginDTO.user = user
            Log.d("Debug", "${user.email} ${user.password}")

            loginRepo.login(login = loginDTO).enqueue(object : Callback<LoginResponseDTO> {
                override fun onFailure(call: Call<LoginResponseDTO>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Post Failed!", Toast.LENGTH_SHORT).show()
                    Log.e("NetworkingError", "Post Failed", t)
                    loginFormLayout.visibility = View.VISIBLE
                    splashScreen.visibility = View.GONE
                }

                override fun onResponse(
                    call: Call<LoginResponseDTO>,
                    response: Response<LoginResponseDTO>
                ) {
                    when (response.code()) {
                        401 -> {
                            Snackbar.make(
                                loginButton,
                                "Usuario/Contraseña Incorrecto",
                                Snackbar.LENGTH_SHORT
                            ).setTextColor(Color.RED).show()
                            loginFormLayout.visibility = View.VISIBLE
                            splashScreen.visibility = View.GONE
                        }
                        200 -> {
                            val headers = response.headers()
                            val rawToken = headers.get("Authorization")
                            if (rawToken == null) {
                                Log.d("Debug", "Authorization Header not found")
                            } else {
                                val lstToken = rawToken.split(" ")
                                val token = lstToken[1]
                                Log.d("Debug", "Token: $token")
                                val sharedPref =
                                    this@LoginActivity.getSharedPreferences(
                                        "Login",
                                        Context.MODE_PRIVATE
                                    )
                                        ?: return
                                with(sharedPref.edit()) {
                                    putString("Token", token)
                                    apply()
                                }
                                val loginResponse = response.body() as LoginResponseDTO
                                sharedPref.edit().putInt("UserId", loginResponse.id).apply()
                                lessorRepo.getLessorByUserId(loginResponse.id, rawToken)
                                    .enqueue(object : Callback<Lessor> {
                                        override fun onFailure(call: Call<Lessor>, t: Throwable) {
                                            Log.e(
                                                "NetworkingDebug",
                                                "Could not connect to back-end",
                                                t
                                            )
                                            loginFormLayout.visibility = View.VISIBLE
                                            splashScreen.visibility = View.GONE
                                            Snackbar.make(
                                                loginButton,
                                                "No existe un usuario de tipo arrendatario registrado con la combinación de Correo/Contraseña",
                                                Snackbar.LENGTH_SHORT
                                            ).show()
                                        }

                                        override fun onResponse(
                                            call: Call<Lessor>,
                                            response: Response<Lessor>
                                        ) {
                                            when (response.code()) {
                                                200 -> {
                                                    if (response.body() != null) {
                                                        loginFormLayout.visibility = View.VISIBLE
                                                        splashScreen.visibility = View.GONE
                                                        Snackbar.make(
                                                            loginButton,
                                                            "Inicio de sesión exitoso",
                                                            Snackbar.LENGTH_SHORT
                                                        ).setTextColor(Color.GREEN).show()
                                                        val lessor = response.body() as Lessor
                                                        sharedPref.edit()
                                                            .putInt("LessorId", lessor.id)
                                                            .apply()
                                                        val loginIntent =
                                                            Intent(
                                                                applicationContext,
                                                                NavigationActivity::class.java
                                                            )
                                                        startActivity(loginIntent)
                                                        finish()
                                                    } else {
                                                        loginFormLayout.visibility = View.VISIBLE
                                                        splashScreen.visibility = View.GONE
                                                        Snackbar.make(
                                                            loginButton,
                                                            "No existe un usuario de tipo arrendatario registrado con estos datos",
                                                            Snackbar.LENGTH_LONG
                                                        ).show()
                                                    }
                                                }
                                                401 -> {
                                                    loginFormLayout.visibility = View.VISIBLE
                                                    splashScreen.visibility = View.GONE
                                                    Snackbar.make(
                                                        loginButton,
                                                        "Los datos no coinciden",
                                                        Snackbar.LENGTH_SHORT
                                                    ).setTextColor(Color.RED).show()
                                                }
                                                else -> {
                                                    Log.d("NetworkingDebug", "Not mapped Error")
                                                }
                                            }
                                        }
                                    })
                            }
                        }
                        else -> Log.d("NetworkingDebug", "Not mapped Error")
                    }
                }
            })
        }
    }

    private fun signUpButton() {
        signUpLink.setOnClickListener {
            Log.d("Debug", "Sign-Up Button Pressed")
            val signUpIntent = Intent(applicationContext, SignUpActivity::class.java)
            startActivity(signUpIntent)
            finish()
        }
    }

}
