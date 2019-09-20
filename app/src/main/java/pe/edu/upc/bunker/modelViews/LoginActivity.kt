package pe.edu.upc.bunker.modelViews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import pe.edu.upc.bunker.R
import pe.edu.upc.bunker.dto.LoginDTO
import pe.edu.upc.bunker.dto.UserLoginDTO
import pe.edu.upc.bunker.models.User
import pe.edu.upc.bunker.repository.LoginRepository
import pe.edu.upc.bunker.repository.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var loginButton : Button
    lateinit var userNameEditText : EditText
    lateinit var passwordEditText : EditText
    private lateinit var loginDTO : LoginDTO
    private lateinit var user : UserLoginDTO


    val loginRepo = RetrofitClientInstance().getRetrofitInstance().create(LoginRepository::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userNameEditText = findViewById(R.id.user_field_edit_text)
        passwordEditText = findViewById(R.id.password_field_edit_text)

        loginButton = findViewById(R.id.login_button)
        pushButton()

    }

    private fun pushButton(){
        Log.d("Debug","Button Pressed")
        loginButton.setOnClickListener {
            Log.d("Debug","Setting email and password to DTO")
            user = UserLoginDTO()
            loginDTO = LoginDTO()
            user.email = userNameEditText.text.toString()
            user.password = userNameEditText.text.toString()

            loginDTO.user = user
            Log.d("Debug","${user.email}+${user.password}")


            loginRepo.login(login = loginDTO,token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwic2NwIjoidXNlciIsImF1ZCI6bnVsbCwiaWF0IjoxNTY4OTQzOTE5LCJleHAiOjE1Njg5NDU3MTksImp0aSI6IjM1MzJmOTdkLWU3MzItNDllYS05ZDJhLWJlM2RkMGE2OTlkYiJ9.wdGyoO4bKfAafYmlGQx5b6G-q38EpuscsNNyqjMu7T4").enqueue(object : Callback<LoginDTO>{
            override fun onFailure(call: Call<LoginDTO>, t: Throwable) {
                Toast.makeText(this@LoginActivity,"Post Failed!",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<LoginDTO>, response: Response<LoginDTO>) {
                Toast.makeText(this@LoginActivity,"Post Succeed",Toast.LENGTH_SHORT).show()
                val status = response.code()

                when(status)
                {
                    401->Toast.makeText(this@LoginActivity,"Unauthorized",Toast.LENGTH_SHORT).show()
                    200->{
                        Toast.makeText(this@LoginActivity,"Success",Toast.LENGTH_SHORT).show()
                        val loginIntent = Intent(applicationContext,NavigationActivity::class.java)
                        startActivity(loginIntent)
                    }
                    else ->Toast.makeText(this@LoginActivity,"Some other status",Toast.LENGTH_SHORT).show()


                }

            }
        })
        }
    }


}
