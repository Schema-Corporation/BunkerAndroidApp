package pe.edu.upc.bunker.dto


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class LessorDTO: Serializable{

    @SerializedName("user")
    @Expose
    var user : UserDTO? = null

    @SerializedName("ruc")
    @Expose
    var ruc : String? = null

    @SerializedName("commercial_name")
    @Expose
    var commercialName : String? = null

    @SerializedName("first_name")
    @Expose
    var firstName : String? = null

    @SerializedName("last_name")
    @Expose
    var lastName : String? = null

    @SerializedName("doc_type")
    @Expose
    var docType : Int? = 0

    @SerializedName("doc_number")
    @Expose
    var docNumber : String? = null

    @SerializedName("phone")
    @Expose
    var phone : String? = null

    @SerializedName("email")
    @Expose
    var email : String? = null
}