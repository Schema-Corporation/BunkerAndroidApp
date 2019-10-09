package pe.edu.upc.bunker.modelViews.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import pe.edu.upc.bunker.R
import pe.edu.upc.bunker.dto.SpaceInfoDTO

class SpaceDetailsActivity : AppCompatActivity() {

    private lateinit var spaceTitleTextView: TextView
    private lateinit var spaceDescriptionTextView: TextView
    private lateinit var spaceAddressTextView: TextView
    private lateinit var spaceRentPriceTextView: TextView
    private lateinit var spaceStatusTextView: TextView
    private lateinit var spaceTypeTextView: TextView
    private lateinit var spacePhotoImageView: ImageView

    private lateinit var backbtn: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_space_details)

        var mySpace = intent.getSerializableExtra("space") as SpaceInfoDTO

        spaceTitleTextView = findViewById(R.id.space_title)
        spaceDescriptionTextView = findViewById(R.id.space_description)
        spaceAddressTextView = findViewById(R.id.space_address)
        spaceRentPriceTextView = findViewById(R.id.space_rent_price)
        spaceStatusTextView = findViewById(R.id.space_status)
        spaceTypeTextView = findViewById(R.id.space_type)
        spacePhotoImageView = findViewById(R.id.space_image)

        backbtn = findViewById(R.id.back_arrow_image)

        onPress()

        spaceTitleTextView.text = mySpace.title
        spaceDescriptionTextView.text = mySpace.description
        spaceAddressTextView.text = mySpace.address
        spaceRentPriceTextView.text = mySpace.rentPrice.toString()

        when(mySpace.status){
            0 -> spaceStatusTextView.text = "Disponible"
            1 -> spaceStatusTextView.text = "No Disponible"
        }

        when(mySpace.spaceType){
            1 -> spaceTypeTextView.text = "Oficina"
            2 -> spaceTypeTextView.text = "Espacio de Trabajo"
            3 -> spaceTypeTextView.text = "Almacén"
        }
        Picasso.get().load(mySpace.firstPhoto).fit().into(spacePhotoImageView)
    }


    fun onPress(){
        backbtn.setOnClickListener{
            val intent = Intent(this@SpaceDetailsActivity, NavigationActivity::class.java)
            startActivity(intent)
        }



    }

}
