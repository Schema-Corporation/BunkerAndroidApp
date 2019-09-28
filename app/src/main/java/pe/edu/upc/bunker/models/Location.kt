package pe.edu.upc.bunker.models

import com.google.gson.annotations.SerializedName

data class Location(
    val address: String,
    @SerializedName("created_at")
    val createdAt: String,
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    @SerializedName("updated_at")
    val updatedAt: String
)