package pe.edu.upc.bunker.modelViews

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pe.edu.upc.bunker.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPref: SharedPreferences = getSharedPreferences("Flags", Context.MODE_PRIVATE)
        val firstRun = sharedPref.getBoolean("FirstRun", true)
        if (firstRun) {
            val onBoardingIntent = Intent(this, OnBoardingActivity::class.java)
            startActivity(onBoardingIntent)
        }
        val loginIntent = Intent(this, LoginActivity::class.java)
        startActivity(loginIntent)
    }
}
