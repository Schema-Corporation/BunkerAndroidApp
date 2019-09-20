package pe.edu.upc.bunker.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class LoginDTO : Serializable{

    @SerializedName("user")
    @Expose
    var user : UserLoginDTO? = null
}