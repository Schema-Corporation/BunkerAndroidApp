package pe.edu.upc.bunker.modelViews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pe.edu.upc.bunker.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val onboardingIntent = Intent(this,OnBoardingActivity::class.java)
        startActivity(onboardingIntent)

    }
}
