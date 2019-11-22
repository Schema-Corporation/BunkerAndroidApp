package pe.edu.upc.bunker.modelViews.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_lessor_form.*
import pe.edu.upc.bunker.R
import pe.edu.upc.bunker.dto.LessorDTO
import pe.edu.upc.bunker.dto.UserDTO
import pe.edu.upc.bunker.repository.LessorRepository
import pe.edu.upc.bunker.repository.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LessorFormActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {


    private lateinit var rucEditText: TextInputEditText
    private lateinit var commercialNameEditText: TextInputEditText
    private lateinit var namesEditText: TextInputEditText
    private lateinit var lastNamesEditText: TextInputEditText
    private lateinit var documentTypeSpinner: Spinner
    private lateinit var documentNumberEditText: TextInputEditText
    private lateinit var celNumberEditText: TextInputEditText

    private lateinit var tycCheckBox: CheckBox

    private lateinit var cancelButton: MaterialButton
    private lateinit var registerButton: MaterialButton

    private val numberRegex = "^[0-9]*$".toRegex()

    private val lessorRepo =
        RetrofitClientInstance().getRetrofitInstance().create(LessorRepository::class.java)

    private val documentTypes = arrayOf("DNI", "Carné de Extranjería", "Pasaporte")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lessor_form)


        val dropdownListAdapter = ArrayAdapter(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            documentTypes
        )

        rucEditText = findViewById(R.id.ruc_register_field)
        commercialNameEditText = findViewById(R.id.commercial_name_field)
        namesEditText = findViewById(R.id.names_field)
        lastNamesEditText = findViewById(R.id.last_names_register_field)
        documentTypeSpinner = findViewById(R.id.doc_type_spinner)

        documentNumberEditText = findViewById(R.id.doc_num_register_field)
        celNumberEditText = findViewById(R.id.cel_num_register_field)

        tycCheckBox = findViewById(R.id.terms_conditions_checkbox)

        cancelButton = findViewById(R.id.cancel_register_button)
        registerButton = findViewById(R.id.register_button)

        documentTypeSpinner.onItemSelectedListener = this
        dropdownListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        documentTypeSpinner.adapter = dropdownListAdapter
        pressCancel()

        pressSignUp()
    }

    private fun pressCancel() {
        cancelButton.setOnClickListener {
            val signUpIntent = Intent(applicationContext, SignUpActivity::class.java)
            startActivity(signUpIntent)
            finish()
        }
    }

    private fun pressSignUp() {
        registerButton.setOnClickListener {
            val ruc = rucEditText.text.toString()
            when {
                ruc.isEmpty() -> til_ruc_register.error = "Este campo es requerido"
                ruc.length != 11 -> til_ruc_register.error = "El ruc debe tener 13 dígitos"
                !ruc.matches(numberRegex) -> til_ruc_register.error = "El ruc debe ser un número"
                else -> til_ruc_register.error = null
            }
            val commercialName = commercialNameEditText.text.toString()
            when{
                commercialName.isEmpty() -> til_commercial_name_register.error = "Este campo es requerido"
                commercialName.length > 30 -> til_commercial_name_register.error =
                    "No puede ser mayor a 30 caracteres"
                else -> til_commercial_name_register.error = null
            }
            val name = namesEditText.text.toString()
            when{
                name.isEmpty() -> til_names_register.error = "Este campo es requerido"
                else -> til_names_register.error = null
            }
            val lastName = lastNamesEditText.text.toString()
            when{
                lastName.isEmpty() -> til_last_names_register.error = "Este campo es requerido"
                else -> til_last_names_register.error = null
            }
            documentTypeSpinner = findViewById(R.id.doc_type_spinner)
            var docType = 1
            when (documentTypeSpinner.selectedItemPosition) {
                0 -> docType = 1
                1 -> docType = 3
                2 -> docType = 5
            }
            Log.d("El tipo de documento es ", docType.toString())
            val docNum = documentNumberEditText.text.toString()
            when{
                docNum.isEmpty() -> til_doc_num_register.error = "Este campo es requerido"
                docType == 1 || docType == 5 -> {
                    if (docNum.length != 8) {
                        til_doc_num_register.error = "El DNI debe tener 8 dígitos"
                    }
                }
                docType == 3 -> {
                    if (docNum.length != 9) {
                        til_doc_num_register.error = "El CE debe tener 9 dígitos"
                    }
                }
                else -> til_doc_num_register.error = null
            }
            val celNum = celNumberEditText.text.toString()
            when {
                celNum.isEmpty() -> til_cel_num_register.error = "Este campo es requerido"
                celNum.length != 9 -> til_cel_num_register.error =
                    "El número de celular debe tener 9 dítigos"
                else -> til_cel_num_register.error = null
            }
            val tyc = when {
                tycCheckBox.isChecked -> true
                else -> false
            }
            if (!tyc) {
                tycCheckBox.error =
                    "Necesita aceptar los términos de uso y condiciones para proceder con su registro"
            }

            if (til_ruc_register.error.isNullOrEmpty() &&
                    til_commercial_name_register.error.isNullOrEmpty() &&
                    til_names_register.error.isNullOrEmpty() &&
                    til_last_names_register.error.isNullOrEmpty() &&
                    til_doc_num_register.error.isNullOrEmpty() &&
                    til_cel_num_register.error.isNullOrEmpty() &&
                    tyc){

                val sharedPreferences = this@LessorFormActivity.getSharedPreferences(
                    "SignUp",
                    Context.MODE_PRIVATE
                )
                val userId = sharedPreferences.getInt("UserId", 0)
                val email = sharedPreferences.getString("UserEmail", "example@gmail.com")
                val token = sharedPreferences.getString("Token", "test") as String
                Log.d("NetworkingDebug", token)
                val authorization = "Bearer $token"

                val lessor = LessorDTO()
                val userLogged = UserDTO()
                userLogged.id = userId

                lessor.user = userLogged
                lessor.email = email
                lessor.ruc = ruc
                lessor.commercialName = commercialName
                lessor.firstName = name
                lessor.lastName = lastName
                lessor.docType = docType
                lessor.docNumber = docNum
                lessor.phone = celNum


                lessorRepo.postLessor(authorization, lessor).enqueue(object : Callback<LessorDTO> {
                    override fun onFailure(call: Call<LessorDTO>, t: Throwable) {
                        Log.e("NetworkingError", "Post Failed", t)
                    }

                    override fun onResponse(call: Call<LessorDTO>, response: Response<LessorDTO>) {
                        Log.d("entro: ", response.code().toString())
                        Log.d("Auth", authorization)
                        when (response.code()) {
                            201 -> {
                                startActivity(
                                    Intent(
                                        this@LessorFormActivity,
                                        NavigationActivity::class.java
                                    )
                                )
                                finish()
                            }
                            204 -> {
                                Log.d("error:", response.toString())
                            }
                        }
                    }

                })
            }

        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
    }
    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

}

