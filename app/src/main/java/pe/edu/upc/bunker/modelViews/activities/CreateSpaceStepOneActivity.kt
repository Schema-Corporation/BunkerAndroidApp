package pe.edu.upc.bunker.modelViews.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
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
    private lateinit var priceEditText: TextInputEditText
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
        priceEditText = findViewById(R.id.price_text_input_edit)
        nextStepButton = findViewById(R.id.step_2_next_button)
        keyPressedLength(widthEditText, 3)
        keyPressedLength(heightEditText, 3)
        keyPressedLength(areaEditText, 4)
        addingCurrency("\$", 5)
        pressNextStepButton()
    }

    private fun pressNextStepButton() {
        nextStepButton.setOnClickListener {

            val width = widthEditText.text.toString()
            when {
                width.isEmpty() -> text_input_width.error = "Este campo es requerido"
                width.toInt() < 0 -> text_input_width.error = "Este campo no puede ser negativo"
                else -> text_input_width.error = null
            }
            spaceTypeTextView = findViewById(R.id.space_type_dropdown)
            var spaceType = 2
            when (spaceTypeTextView.text.toString()) {
                "Oficina" -> spaceType = 0
                "Espacio de trabajo" -> spaceType = 1
                "Almacén" -> spaceType = 2
            }
            val height = heightEditText.text.toString()
            when {
                height.isEmpty() -> text_input_height.error = "Este campo es requerido"
                height.toInt() < 0 -> text_input_height.error = "Este campo no puede ser negativo"
                else -> text_input_height.error = null
            }
            val area = areaEditText.text.toString()
            when {
                area.isEmpty() -> text_input_area.error = "Este campo es requerido"
                area.toInt() < 0 -> text_input_area.error = "Este campo no puede ser negativo"
                else -> text_input_area.error = null
            }
            val title = titleEditText.text.toString()
            if (title.isEmpty()) {
                text_input_title.error = "Este campo es requerido"
            }
            priceEditText.error = null
            val price = priceEditText.text.toString().split("\$")[1]
            if (price.isEmpty()) {
                price_input_layout.error = "Este campo es requerido"
            }
            if (text_input_width.error.isNullOrEmpty() &&
                text_input_height.error.isNullOrEmpty() &&
                text_input_area.error.isNullOrEmpty() &&
                text_input_title.error.isNullOrEmpty() &&
                price_input_layout.error.isNullOrEmpty() &&
                areaEditText.error.isNullOrEmpty() &&
                widthEditText.error.isNullOrEmpty() &&
                heightEditText.error.isNullOrEmpty() &&
                priceEditText.error.isNullOrEmpty()
            ) {
                dbHandler.startSpace()

                val sharedPreferences = this@CreateSpaceStepOneActivity.getSharedPreferences(
                    "Login",
                    Context.MODE_PRIVATE
                )
                val userId = sharedPreferences.getInt("UserId", 0)
                val token = sharedPreferences.getString("Token", "test")
                Log.d("NetworkingDebug", token)
                val authorization = "Bearer $token"


                lessorRepo.getLessorByUserId(userId, authorization)
                    .enqueue(object : Callback<Lessor> {
                        override fun onFailure(call: Call<Lessor>, t: Throwable) {
                            Log.e("NetworkingError", "Failed Get", t)
                        }

                        override fun onResponse(call: Call<Lessor>, response: Response<Lessor>) {
                            Log.d("NetworkingDebug", call.request().body().toString())
                            val body = response.body()
                            val lessorId = body!!.id
                            dbHandler.addFirstStep(
                                height = height.toDouble(),
                                width = width.toDouble(),
                                area = area.toDouble(),
                                spaceType = spaceType,
                                price = price,
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

    private fun keyPressedLength(editTextView: EditText, fieldLength: Int) {
        editTextView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                editTextView.removeTextChangedListener(this)
                if (p0 != null) {
                    if (p0.length > fieldLength) {
                        editTextView.error =
                            "Se acepta un máximo de $fieldLength dígitos para este campo"
                    } else {
                        editTextView.error = null
                    }
                    if (p0.isEmpty()) {
                        return
                    }
                    editTextView.addTextChangedListener(this)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })
    }

    private fun addingCurrency(currency: String, maxLength: Int) {
        priceEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                priceEditText.removeTextChangedListener(this)
                if (s != null && s.length == 1 && s[0] != currency[0])
                    s.replace(0, s.length, "$currency${s[0]}")
                if (s != null && s.length > maxLength + 1) {
                    priceEditText.error =
                        "Se acepta un máximo de $maxLength dígitos para este campo"
                } else {
                    priceEditText.error = null
                }
                priceEditText.addTextChangedListener(this)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }
}
