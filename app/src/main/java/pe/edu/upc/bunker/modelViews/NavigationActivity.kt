package pe.edu.upc.bunker.modelViews

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pe.edu.upc.bunker.R

class NavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        startActivity(Intent(applicationContext, MapsActivity::class.java))
        finish()
    }
}
