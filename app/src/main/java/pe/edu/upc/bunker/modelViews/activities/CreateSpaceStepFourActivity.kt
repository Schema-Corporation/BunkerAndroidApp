package pe.edu.upc.bunker.modelViews.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_create_space_step_four.*
import pe.edu.upc.bunker.R
import pe.edu.upc.bunker.dbHelper.BunkerDBHelper
import pe.edu.upc.bunker.dto.createSpace.SpaceCreateDTO
import pe.edu.upc.bunker.models.Space
import pe.edu.upc.bunker.repository.RetrofitClientInstance
import pe.edu.upc.bunker.repository.SpacesRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateSpaceStepFourActivity : AppCompatActivity() {
    private  lateinit var wifiIcon: ImageView
    private  lateinit var lightIcon: ImageView
    private  lateinit var callIcon: ImageView
    private  lateinit var printIcon: ImageView
    private  lateinit var kitchenIcon: ImageView
    private  lateinit var waterIcon: ImageView
    private lateinit var descriptionEditText: EditText

    private var wifiStatus: Int = 0
    private var lightStatus: Int = 0
    private var callStatus: Int = 0
    private var printStatus: Int = 0
    private var kitchenStatus: Int = 0
    private var waterStatus: Int = 0

    private val spacesRepo = RetrofitClientInstance().getRetrofitInstance().create(SpacesRepository::class.java)

    private val dbHandler = BunkerDBHelper(this, null)

    private lateinit var spaceCreateDTO: SpaceCreateDTO

    private lateinit var finishButton : MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_space_step_four)

        wifiIcon = findViewById(R.id.step_4_wifi_icon)
        lightIcon = findViewById(R.id.step_4_light_icon)
        callIcon = findViewById(R.id.step_4_call_icon)
        printIcon = findViewById(R.id.step_4_print_icon)
        kitchenIcon = findViewById(R.id.step_4_kitchen_icon)
        waterIcon = findViewById(R.id.step_4_water_icon)
        descriptionEditText = findViewById(R.id.description_input_edit_text)

        spaceCreateDTO = SpaceCreateDTO()

        selectServiceIcon()

        finishButton = findViewById(R.id.step_4_finish_creation)
        createSpace()
    }

    private fun selectServiceIcon() {

        wifiIcon.setOnClickListener {
            when(wifiStatus){
                0-> {
                    wifiStatus=1
                    wifiIcon.setImageDrawable(ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.ic_wifi_on_24dp
                    ))
                }
                1-> {
                    wifiStatus=0
                    wifiIcon.setImageDrawable(ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.ic_wifi_off_24dp
                    ))
                }
            }
        }

        lightIcon.setOnClickListener {
            when(lightStatus){
                0-> {
                    lightStatus=1
                    lightIcon.setImageDrawable(ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.ic_wb_incandescent_on_24dp
                    ))
                }
                1-> {
                    lightStatus=0
                    lightIcon.setImageDrawable(ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.ic_wb_incandescent_off_24dp
                    ))
                }
            }
        }

        callIcon.setOnClickListener {
            when(callStatus){
                0->{
                    callStatus=1
                    callIcon.setImageDrawable(ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.ic_call_on_24dp
                    ))
                }
                1->{
                    callStatus=0
                    callIcon.setImageDrawable(ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.ic_call_off_24dp
                    ))
                }
            }
        }

        printIcon.setOnClickListener {
            when(printStatus){
                0->{
                    printStatus=1
                    printIcon.setImageDrawable(ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.ic_print_on_24dp
                    ))
                }
                1->{
                    printStatus=0
                    printIcon.setImageDrawable(ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.ic_print_off_24dp
                    ))
                }
            }
        }

        kitchenIcon.setOnClickListener {
            when(kitchenStatus){
                0->{
                    kitchenStatus=1
                    kitchenIcon.setImageDrawable(ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.ic_restaurant_on_24dp
                    ))
                }
                1->{
                    kitchenStatus=0
                    kitchenIcon.setImageDrawable(ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.ic_restaurant_off_24dp
                    ))
                }
            }
        }

        waterIcon.setOnClickListener {
            when(waterStatus){
                0->{
                    waterStatus=1
                    waterIcon.setImageDrawable(ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.ic_water_on_24dp
                    ))
                }
                1->{
                    waterStatus=0
                    waterIcon.setImageDrawable(ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.ic_water_off_24dp
                    ))
                }
            }
        }
    }

    private fun createSpace() {
        finishButton.setOnClickListener {
            val listServices = ArrayList<Int>()
            if (wifiStatus == 1) {
                listServices.add(1)
            }
            if (lightStatus == 1) {
                listServices.add(2)
            }
            if (callStatus == 1) {
                listServices.add(3)
            }
            if (printStatus == 1) {
                listServices.add(4)
            }
            if (kitchenStatus == 1) {
                listServices.add(5)
            }
            if (waterStatus == 1) {
                listServices.add(6)
            }

            val description = descriptionEditText.text.toString()
            if (description.isEmpty()) {
                description_input_layout.error = "Este campo es requerido"
            }
            if (description_input_layout.error == null) {
                dbHandler.addFourthStep(listServices, description)

                spaceCreateDTO = dbHandler.getSpaceFromDb

                val sharedPreferences = this@CreateSpaceStepFourActivity.getSharedPreferences(
                    "Login",
                    Context.MODE_PRIVATE
                )
                val token = sharedPreferences.getString("Token", "test") as String


                spacesRepo.postSpace(spaceCreateDTO, "Bearer $token")
                    .enqueue(object : Callback<Space> {
                        override fun onFailure(call: Call<Space>, t: Throwable) {
                            Log.e("NetworkingError", "Post Failed", t)
                        }

                        override fun onResponse(call: Call<Space>, response: Response<Space>) {
                            when (response.code()) {
                                201 -> {
                                    startActivity(
                                        Intent(
                                            this@CreateSpaceStepFourActivity,
                                            NavigationActivity::class.java
                                        )
                                    )
                                    finish()
                                }
                                401 -> {
                                    Snackbar.make(
                                        finishButton,
                                        "Por favor vuelve a iniciar sesión que tu sesión a expírado",
                                        Snackbar.LENGTH_SHORT
                                    ).show()
                                    startActivity(
                                        Intent(
                                            this@CreateSpaceStepFourActivity,
                                            LoginActivity::class.java
                                        )
                                    )
                                    finish()
                                }
                                204 -> {
                                    Snackbar.make(
                                        finishButton,
                                        "Ups! Hubo un error. Inténtalo de nuevo",
                                        Snackbar.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }

                    })
            }

        }
    }
}
