package pe.edu.upc.bunker.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class UserLoginDTO : Serializable{

    @SerializedName("email")
    @Expose
    var email : String? = null

    @SerializedName("password")
    @Expose
    var password : String? = null
}