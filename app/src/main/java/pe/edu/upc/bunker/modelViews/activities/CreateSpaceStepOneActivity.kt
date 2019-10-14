package pe.edu.upc.bunker.modelViews.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_create_space_step_one.*
import pe.edu.upc.bunker.R
import pe.edu.upc.bunker.dbHelper.BunkerDBHelper
import pe.edu.upc.bunker.models.Lessor
import pe.edu.upc.bunker.repository.LessorRepository
import pe.edu.upc.bunker.repository.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateSpaceStepOneActivity : AppCompatActivity() {

    private lateinit var nextStepButton: Button

    private lateinit var widthEditText: TextInputEditText
    private lateinit var heightEditText: TextInputEditText
    private lateinit var areaEditText: TextInputEditText
    private lateinit var spaceTypeTextView: AutoCompleteTextView
    private lateinit var titleEditText: TextInputEditText
    private lateinit var descriptionEditText: TextInputEditText
    private val dbHandler = BunkerDBHelper(this, null)

    private val spaceTypes = arrayOf("Oficina", "Espacio de trabajo", "Almacén")

    private val lessorRepo =
        RetrofitClientInstance().getRetrofitInstance().create(LessorRepository::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_space_step_one)

        dbHandler.onUpgrade(dbHandler.writableDatabase, 0, 0)

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
            text_input_width.error = null
            if (width.isNullOrEmpty()) {
                text_input_width.error = "Este campo es requerido"
            } else if (width.toInt() < 0) {
                text_input_width.error = "Este campo no puede ser negativo"
            }
            spaceTypeTextView = findViewById(R.id.space_type_dropdown)
            var spaceType = 0
            when (spaceTypeTextView.text.toString()) {
                "Oficina" -> spaceType = 0
                "Espacio de trabajo" -> spaceType = 1
                "Almacén" -> spaceType = 2
            }
            val height = heightEditText.text.toString()
            text_input_height.error = null
            if (height.isNullOrEmpty()) {
                text_input_height.error = "Este campo es requerido"
            } else if (height.toInt() < 0) {
                text_input_height.error = "Este campo no puede ser negativo"
            }
            val area = areaEditText.text.toString()
            text_input_area.error = null
            if (area.isNullOrEmpty()) {
                text_input_area.error = "Este campo es requerido"
            } else if (area.toInt() < 0) {
                text_input_area.error = "Este campo no puede ser negativo"
            }
            val title = titleEditText.text.toString()
            if (title.isNullOrEmpty()) {
                text_input_title.error = "Este campo es requerido"
            }
            text_input_description.error = null
            val description = descriptionEditText.text.toString()
            if (description.isNullOrEmpty()) {
                text_input_description.error = "Este campo es requerido"
            }
            if (text_input_width.error.isNullOrEmpty() &&
                text_input_height.error.isNullOrEmpty() &&
                text_input_area.error.isNullOrEmpty() &&
                text_input_title.error.isNullOrEmpty() &&
                text_input_description.error.isNullOrEmpty()
            ) {
                dbHandler.startSpace()

                val sharedPreferences = this@CreateSpaceStepOneActivity.getSharedPreferences(
                    "Login",
                    Context.MODE_PRIVATE
                )
                val userId = sharedPreferences.getInt("UserId", 0)
                val token = sharedPreferences.getString("Token", "test")
                val authorization = "Bearer $token"

                lessorRepo.getLessorByUserId(userId, authorization).enqueue(object: Callback<Lessor> {
                    override fun onFailure(call: Call<Lessor>, t: Throwable) {
                        // Manejar excepción
                        Log.e("NetworkingError", "Failed Get", t)
                    }

                    override fun onResponse(call: Call<Lessor>, response: Response<Lessor>) {

                        val body = response.body()
                        val lessorId = body!!.id

                        dbHandler.addFirstStep(
                            height = height.toDouble(),
                            width = width.toDouble(),
                            area = area.toDouble(),
                            spaceType = spaceType,
                            description = description,
                            title = title,
                            lessorId = lessorId
                        )
                        val cursor = dbHandler.getAllSpaces()
                        cursor!!.moveToFirst()
                        Log.d(
                            "DBDebug",
                            "ID: ${cursor.getString(cursor.getColumnIndex(BunkerDBHelper.COLUMN_ID))}"
                        )
                        Log.d(
                            "DBDebug",
                            "TITLE: ${cursor.getString(cursor.getColumnIndex(BunkerDBHelper.COLUMN_TITLE))}"
                        )
                        cursor.close()
                        val nextStepIntent =
                            Intent(applicationContext, CreateSpaceStepTwoActivity::class.java)
                        startActivity(nextStepIntent)
                        finish()
                    }

                })
            }
        }
    }
}
