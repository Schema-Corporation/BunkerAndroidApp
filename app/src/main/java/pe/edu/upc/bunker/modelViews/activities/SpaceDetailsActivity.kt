package pe.edu.upc.bunker.modelViews.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
    private lateinit var auth: FirebaseAuth
    private lateinit var spaceVisitsTextView: TextView

    private lateinit var backbtn: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_space_details)
        auth = FirebaseAuth.getInstance()
        auth.signInAnonymously().addOnSuccessListener {
            Log.d("FireBaseDebug", "Signed In successfully to FireBase")
        }.addOnFailureListener {
            Log.e("FireBaseDebug", "Could not sign in to FireBase", it)
        }
        val mySpace = intent.getSerializableExtra("space") as SpaceInfoDTO

        spaceTitleTextView = findViewById(R.id.space_title)
        spaceDescriptionTextView = findViewById(R.id.space_description)
        spaceAddressTextView = findViewById(R.id.space_address)
        spaceRentPriceTextView = findViewById(R.id.space_rent_price)
        spaceStatusTextView = findViewById(R.id.space_status)
        spaceTypeTextView = findViewById(R.id.space_type)
        spacePhotoImageView = findViewById(R.id.space_image)
        spaceVisitsTextView = findViewById(R.id.uniqueVisitorsTextView)
        backbtn = findViewById(R.id.back_arrow_image)

        onPress()

        spaceTitleTextView.text = mySpace.title
        spaceDescriptionTextView.text = mySpace.description
        spaceAddressTextView.text = mySpace.address.split(",")[0]
        spaceRentPriceTextView.text = mySpace.rentPrice.toString()

        when (mySpace.status) {
            0 -> spaceStatusTextView.text = "Disponible"
            1 -> spaceStatusTextView.text = "No Disponible"
        }

        when (mySpace.spaceType) {
            0 -> spaceTypeTextView.text = "Oficina"
            1 -> spaceTypeTextView.text = "Oficina"
            2 -> spaceTypeTextView.text = "Espacio de Trabajo"
            3 -> spaceTypeTextView.text = "Almacén"
        }
        Picasso.get().load(mySpace.firstPhoto).fit().into(spacePhotoImageView)
        loadVisits(mySpace)
    }

    private fun loadVisits(space: SpaceInfoDTO) {
        if (space.id != 0) {
            val database = FirebaseDatabase.getInstance()
            val myRef =
                database.getReference("visits").child(space.id.toString())
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Log.w("FireBaseDebug", "Failed to read value.", error.toException())
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d(
                        "FireBaseDebug",
                        "Value of visits has been updated or read for space ${space.id}"
                    )
                    spaceVisitsTextView.text = dataSnapshot.childrenCount.toString()
                }
            })
        } else {
            spaceVisitsTextView.text = "0"
        }
    }

    private fun onPress() {
        backbtn.setOnClickListener {
            val intent = Intent(this@SpaceDetailsActivity, NavigationActivity::class.java)
            startActivity(intent)
        }
    }
}
