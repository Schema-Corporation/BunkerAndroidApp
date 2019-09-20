package pe.edu.upc.bunker.models


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("created_at")
    val createdAt: String,
    val email: String,
    val id: Int,
    @SerializedName("updated_at")
    val updatedAt: String
)