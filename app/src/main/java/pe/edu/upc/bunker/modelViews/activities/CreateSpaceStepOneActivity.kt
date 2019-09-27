package pe.edu.upc.bunker.modelViews.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_create_space_step_one.*
import pe.edu.upc.bunker.R

class CreateSpaceStepOneActivity : AppCompatActivity() {

    private lateinit var nextStepButton: Button


    private lateinit var widthEditText: TextInputEditText
    private lateinit var heightEditText: TextInputEditText
    private lateinit var areaEditText: TextInputEditText
    private lateinit var spaceTypeTextView: AutoCompleteTextView
    private lateinit var titleEditText: TextInputEditText
    private lateinit var descriptionEditText: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_space_step_one)

        val dropdownListAdapter = ArrayAdapter(
            this,
            R.layout.dropdown_menu_space_type_popup_item,
            spaceTypes
        )
        widthEditText = findViewById(R.id.input_width_edit_text)
        heightEditText = findViewById(R.id.input_height_edit_text)
        areaEditText = findViewById(R.id.input_area_edit_text)
        spaceTypeTextView = findViewById(R.id.space_type_dropdown)
        spaceTypeTextView.setAdapter(dropdownListAdapter)
        titleEditText = findViewById(R.id.input_title_edit_text)
        descriptionEditText = findViewById(R.id.input_description_edit_text)
        nextStepButton = findViewById(R.id.step_2_next_button)
        pressNextStepButton()


    }

    private fun pressNextStepButton() {
        nextStepButton.setOnClickListener {

            val width = widthEditText.text.toString()
            if (width.isNullOrEmpty()) {
                text_input_width.error = "Este campo es requerido"
            } else if (width.toInt() < 0) {
                text_input_width.error = "Este campo no puede ser negativo"
            }

            val height = heightEditText.text.toString()
            if (height.isNullOrEmpty()) {
                text_input_height.error = "Este campo es requerido"
            } else if (height.toInt() < 0) {
                text_input_height.error = "Este campo no puede ser negativo"
            }

            val area = areaEditText.text.toString()
            if (area.isNullOrEmpty()) {
                text_input_area.error = "Este campo es requerido"
            } else if (area.toInt() < 0) {
                text_input_area.error = "Este campo no puede ser negativo"
            }
            val title = titleEditText.text.toString()
            if (title.isNullOrEmpty()) {
                text_input_title.error = "Este campo es requerido"
            }
            val description = descriptionEditText.text.toString()
            if (description.isNullOrEmpty()) {
                text_input_description.error = "Este campo es requerido"
            }
            val spaceType = spaceTypeTextView.text.toString()
            if (text_input_width.error.isNullOrEmpty() &&
                text_input_height.error.isNullOrEmpty() &&
                text_input_area.error.isNullOrEmpty() &&
                space_type_dropdown.error.isNullOrEmpty() &&
                text_input_title.error.isNullOrEmpty() &&
                text_input_description.error.isNullOrEmpty()
            ) {
                val nextStepIntent =
                    Intent(applicationContext, CreateSpaceStepTwoActivity::class.java)
                getDataFromForm(nextStepIntent)
                startActivity(nextStepIntent)
                finish()
            }
        }
    }


    private fun getDataFromForm(nextStepIntent: Intent) {
        widthEditText = findViewById(R.id.input_width_edit_text)
        val spaceWidth = widthEditText.text.toString()

        heightEditText = findViewById(R.id.input_height_edit_text)
        val spaceHeight = heightEditText.text.toString()

        areaEditText = findViewById(R.id.input_area_edit_text)
        val spaceArea = areaEditText.text.toString()

        spaceTypeTextView = findViewById(R.id.space_type_dropdown)
        var spaceType = 0
        when (spaceTypeTextView.text.toString()) {
            "Oficina" -> spaceType = 0
            "Espacio de trabajo" -> spaceType = 1
            "Almacén" -> spaceType = 2
        }

        titleEditText = findViewById(R.id.input_title_edit_text)
        val spaceTitle = titleEditText.text.toString()

        descriptionEditText = findViewById(R.id.input_description_edit_text)
        val spaceDescription = descriptionEditText.text.toString()

        nextStepIntent.putExtra("spaceWidth", spaceWidth)
        nextStepIntent.putExtra("spaceHeight", spaceHeight)
        nextStepIntent.putExtra("spaceArea", spaceArea)
        nextStepIntent.putExtra("spaceType", spaceType)
        nextStepIntent.putExtra("spaceTitle", spaceTitle)
        nextStepIntent.putExtra("spaceDescription", spaceDescription)
    }

    private val spaceTypes = arrayOf("Oficina", "Espacio de trabajo", "Almacén")

}
