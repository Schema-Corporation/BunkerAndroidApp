package pe.edu.upc.bunker.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class LoginResponseDTO : Serializable {

    @Expose
    var id: Int = 0

    @SerializedName("email")
    @Expose
    var email: String? = null
}