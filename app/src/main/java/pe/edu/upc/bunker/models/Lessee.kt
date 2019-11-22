package pe.edu.upc.bunker.models


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Lessee(
    @SerializedName("created_at")
    @Expose
    val createdAt: String,

    @SerializedName("doc_number")
    @Expose
    val docNumber: Any,

    @SerializedName("doc_type")
    @Expose
    val docType: Int,

    @Expose
    val email: String,

    @SerializedName("first_name")
    @Expose
    val firstName: String,

    @Expose
    val id: Int,

    @SerializedName("last_name")
    @Expose
    val lastName: String,

    @Expose
    val phone: String,

    @SerializedName("updated_at")
    @Expose
    val updatedAt: String,

    @Expose
    val user: User
)