package pe.edu.upc.bunker.models


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("created_at")
    @Expose
    val createdAt: String,

    @Expose
    val email: String,

    @Expose
    val id: Int,

    @SerializedName("updated_at")
    @Expose
    val updatedAt: String
)