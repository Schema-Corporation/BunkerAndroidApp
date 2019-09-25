package pe.edu.upc.bunker.modelViews.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import pe.edu.upc.bunker.R

class CreateSpaceStepOneActivity : AppCompatActivity() {

    private lateinit var spaceType : AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_space_step_one)

        val dropdownListAdapter = ArrayAdapter(
            this,
            R.layout.dropdown_menu_space_type_popup_item,
            SPACE_TYPES
        )

        spaceType = findViewById(R.id.space_type_dropdown)
        spaceType.setAdapter(dropdownListAdapter)
        
    }

    private val SPACE_TYPES = arrayOf("Oficina", "Espacio de trabajo", "Almacén")

}
