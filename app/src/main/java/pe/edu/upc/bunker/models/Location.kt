package pe.edu.upc.bunker.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Location(
    @Expose
    val address: String,

    @SerializedName("created_at")
    @Expose
    val createdAt: String,

    @Expose
    val id: Int,

    @Expose
    val latitude: Double,

    @Expose
    val longitude: Double,

    @SerializedName("updated_at")
    @Expose
    val updatedAt: String
)