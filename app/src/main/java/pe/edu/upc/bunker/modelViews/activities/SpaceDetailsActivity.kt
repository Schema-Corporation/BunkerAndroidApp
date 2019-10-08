package pe.edu.upc.bunker.modelViews.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pe.edu.upc.bunker.R
import pe.edu.upc.bunker.dto.SpaceDTO

class SpaceDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_space_details)

        var mySpace = intent.getSerializableExtra("space") as SpaceDTO
    }

}
