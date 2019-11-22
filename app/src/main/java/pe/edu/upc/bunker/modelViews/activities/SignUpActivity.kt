package pe.edu.upc.bunker.modelViews.activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import pe.edu.upc.bunker.R
import pe.edu.upc.bunker.dto.LoginDTO
import pe.edu.upc.bunker.dto.LoginResponseDTO
import pe.edu.upc.bunker.dto.UserLoginDTO
import pe.edu.upc.bunker.repository.LoginRepository
import pe.edu.upc.bunker.repository.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    private lateinit var userNameEditLayout: TextInputLayout
    private lateinit var userNameEditText: TextInputEditText
    private lateinit var passwordEditLayout: TextInputLayout
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var cancelButton: Button
    private lateinit var signUpButton: Button
    private val loginRepo =
        RetrofitClientInstance().getRetrofitInstance().create(LoginRepository::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        userNameEditLayout = findViewById(R.id.til_user_name_sign_up)
        userNameEditText = findViewById(R.id.user_name_sign_up_field)
        passwordEditLayout = findViewById(R.id.til_password_sign_up)
        passwordEditText = findViewById(R.id.password_sign_up_field)
        cancelButton = findViewById(R.id.cancel_sign_up_button)
        signUpButton = findViewById(R.id.sign_up_button)

        pressCancel()
        pressSignUp()
    }

    private fun pressCancel() {
        cancelButton.setOnClickListener {
            val loginIntent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(loginIntent)
            finish()
        }
    }

    private fun pressSignUp() {
        signUpButton.setOnClickListener {
            val username = userNameEditText.text.toString()
            if (!validateEmail(username)) {
                userNameEditLayout.error = "Username is not valid!"
            } else {
                userNameEditLayout.error = null
            }
            val password = passwordEditText.text.toString()
            if (password.isBlank()) {
                passwordEditLayout.error = "Password cannot be empty"
            } else {
                passwordEditLayout.error = null
            }
//borrar luego
           /* val loginIntent =
                Intent(
                    applicationContext,
                    LessorFormActivity::class.java
                    //NavigationActivity::class.java
                )
            startActivity(loginIntent)
            finish()*/
            if (userNameEditLayout.error.isNullOrEmpty() && passwordEditLayout.error.isNullOrEmpty()) {
                val user = UserLoginDTO()
                user.email = username
                user.password = password

                Log.d("Debug", "${user.email} : ${user.password}")

                val loginDTO = LoginDTO()
                loginDTO.user = user
                loginRepo.signUp(signUp = loginDTO).enqueue(object : Callback<Void> {
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(this@SignUpActivity, "Post Failed!", Toast.LENGTH_SHORT)
                            .show()
                        Log.d("Debug", "Post Failed for SignUp", t)
                    }

                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        Toast.makeText(this@SignUpActivity, "Post Succeed", Toast.LENGTH_SHORT)
                            .show()
                        Log.d("Debug", "Response Code: ${response.code()}")
                        when (response.code()) {
                            200 -> {
                                Snackbar.make(
                                    signUpButton,
                                    "Se ha validado correctamente su registro",
                                    Snackbar.LENGTH_SHORT
                                ).show()

                                loginRepo.login(login = loginDTO)
                                    .enqueue(object : Callback<LoginResponseDTO> {
                                        override fun onFailure(
                                            call: Call<LoginResponseDTO>,
                                            t: Throwable
                                        ) {
                                            Snackbar.make(
                                                signUpButton,
                                                "Verifique si tiene conexión a Internet",
                                                Snackbar.LENGTH_SHORT
                                            ).setTextColor(Color.RED).show()
                                            Log.e("NetworkingError", "Login Failed", t)
                                        }

                                        override fun onResponse(
                                            call: Call<LoginResponseDTO>,
                                            response: Response<LoginResponseDTO>
                                        ) {
                                            Log.d("Login", "Login successful")

                                            when (response.code()) {
                                                401 -> {
                                                    Log.e(
                                                        "NetworkingError",
                                                        "Somehow register was unauthorized"
                                                    )
                                                }
                                                200 -> {
                                                    val headers = response.headers()
                                                    val rawToken = headers.get("Authorization")
                                                    if (rawToken == null) {
                                                        Log.d(
                                                            "Debug",
                                                            "Authorization Header not found"
                                                        )
                                                    } else {
                                                        val lstToken = rawToken.split(" ")
                                                        val token = lstToken[1]
                                                        Log.d("Debug", "Token: $token")
                                                        val sharedPref =
                                                            this@SignUpActivity.getSharedPreferences(
                                                                "SignUp",
                                                                Context.MODE_PRIVATE
                                                            )
                                                                ?: return
                                                        with(sharedPref.edit()) {
                                                            putString("Token", token)
                                                            apply()
                                                        }

                                                        val loginResponse = response.body() as LoginResponseDTO
                                                        sharedPref.edit().putInt("UserId", loginResponse.id).apply()

                                                        sharedPref.edit().putString("UserEmail", user.email).apply()

                                                        val loginIntent =
                                                            Intent(
                                                                applicationContext,
                                                                LessorFormActivity::class.java
                                                                //NavigationActivity::class.java
                                                            )
                                                        startActivity(loginIntent)
                                                        finish()
                                                    }
                                                }
                                                else -> Toast.makeText(
                                                    this@SignUpActivity,
                                                    "Some other status",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                    })

                            }
                            else -> Toast.makeText(
                                this@SignUpActivity,
                                "Some other status",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
           }
        }
    }

    private fun validateEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
