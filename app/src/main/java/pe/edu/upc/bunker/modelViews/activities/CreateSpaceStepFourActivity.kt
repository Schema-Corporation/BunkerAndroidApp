package pe.edu.upc.bunker.modelViews.activities

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import pe.edu.upc.bunker.R
import pe.edu.upc.bunker.dbHelper.BunkerDBHelper
import pe.edu.upc.bunker.dto.createSpace.SpaceCreateDTO

class CreateSpaceStepFourActivity : AppCompatActivity() {
    private  lateinit var wifiIcon: ImageView
    private  lateinit var lightIcon: ImageView
    private  lateinit var callIcon: ImageView
    private  lateinit var printIcon: ImageView
    private  lateinit var kitchenIcon: ImageView
    private  lateinit var waterIcon: ImageView

    private var wifiStatus: Int = 0
    private var lightStatus: Int = 0
    private var callStatus: Int = 0
    private var printStatus: Int = 0
    private var kitchenStatus: Int = 0
    private var waterStatus: Int = 0

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
            dbHandler.addServices(
                wifiStatus,
                lightStatus,
                callStatus,
                printStatus,
                kitchenStatus,
                waterStatus
            )
            spaceCreateDTO = dbHandler.getSpaceFromDb
        }
    }
}
