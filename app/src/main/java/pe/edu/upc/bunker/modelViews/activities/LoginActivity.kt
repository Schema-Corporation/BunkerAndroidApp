package pe.edu.upc.bunker.modelViews.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import pe.edu.upc.bunker.R
import pe.edu.upc.bunker.dto.LoginDTO
import pe.edu.upc.bunker.dto.LoginResponseDTO
import pe.edu.upc.bunker.dto.UserLoginDTO
import pe.edu.upc.bunker.repository.LoginRepository
import pe.edu.upc.bunker.repository.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    lateinit var loginButton: Button
    lateinit var userNameEditText: TextInputEditText
    lateinit var passwordEditText: TextInputEditText
    lateinit var signupLink: TextView
    private lateinit var loginDTO: LoginDTO
    private lateinit var user: UserLoginDTO

    private val loginRepo =
        RetrofitClientInstance().getRetrofitInstance().create(LoginRepository::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userNameEditText = findViewById(R.id.user_field_edit_text)
        passwordEditText = findViewById(R.id.password_field_edit_text)
        signupLink = findViewById(R.id.register_link)

        loginButton = findViewById(R.id.login_button)
        pushButton()
        signUpButton()
    }

    private fun pushButton() {
        Log.d("Debug", "Login Button Pressed")
        loginButton.setOnClickListener {
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
                }

                override fun onResponse(call: Call<LoginResponseDTO>, response: Response<LoginResponseDTO>) {
                    Toast.makeText(this@LoginActivity, "Post Succeed", Toast.LENGTH_SHORT).show()

                    val body = response.body()
                    val userResponse = body!!

                    when (response.code()) {
                        401 -> Toast.makeText(
                            this@LoginActivity,
                            "Password/Username not match",
                            Toast.LENGTH_SHORT
                        ).show()
                        200 -> {
                            Toast.makeText(
                                this@LoginActivity,
                                "Credentials confirmed",
                                Toast.LENGTH_SHORT
                            ).show()

                            val headers = response.headers()
                            val rawToken = headers.get("Authorization")
                            if (rawToken == null) {
                                Log.d("Debug", "Authorization Header not found")
                            } else {
                                val lstToken = rawToken.split(" ")
                                val token = lstToken[1]
                                Log.d("Debug", "Token: $token")
                                val sharedPref =
                                    this@LoginActivity.getSharedPreferences("Login", Context.MODE_PRIVATE)
                                        ?: return
                                with(sharedPref.edit()) {
                                    putString("Token", token)
                                    apply()
                                }
                                sharedPref.edit().putInt("UserId", userResponse.id!!).apply()
                                val loginIntent =
                                    Intent(applicationContext, NavigationActivity::class.java)
                                startActivity(loginIntent)
                                finish()
                            }
                        }
                        else -> Toast.makeText(
                            this@LoginActivity,
                            "Some other status",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
        }
    }

    private fun signUpButton() {
        signupLink.setOnClickListener {
            Log.d("Debug", "Sign-Up Button Pressed")

            val signUpIntent = Intent(applicationContext, SignUpActivity::class.java)
            startActivity(signUpIntent)
            finish()
        }
    }

}
