package pe.edu.upc.bunker.modelViews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import pe.edu.upc.bunker.R

class LoginActivity : AppCompatActivity() {

    lateinit var loginButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton = findViewById(R.id.login_button)
    }

    fun pressButton() {
        loginButton.setOnClickListener {

        }
    }



}
