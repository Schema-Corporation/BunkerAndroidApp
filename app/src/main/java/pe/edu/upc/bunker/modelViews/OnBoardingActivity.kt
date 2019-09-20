package pe.edu.upc.bunker.modelViews

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import pe.edu.upc.bunker.R

class OnBoardingActivity : AppCompatActivity() {

    lateinit var startButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        startButton = findViewById(R.id.startButton)
        pressButton()
    }
    private fun pressButton() {
        startButton.setOnClickListener {
            val sharedPref: SharedPreferences = getSharedPreferences("Flags", Context.MODE_PRIVATE)
            sharedPref.edit().putBoolean("FirstRun",false).apply()
            val loginIntent = Intent(this,LoginActivity::class.java)
            startActivity(loginIntent)
            finish()
        }
    }
}
