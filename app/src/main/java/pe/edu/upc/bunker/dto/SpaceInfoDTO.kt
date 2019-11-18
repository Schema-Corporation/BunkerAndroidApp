package pe.edu.upc.bunker.dto


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SpaceInfoDTO (
    @Expose
    val address: String,

    @Expose
    val description: String,

    @SerializedName("first_photo")
    @Expose
    val firstPhoto: String,

    @Expose
    val id: Int,

    @SerializedName("rent_price")
    @Expose
    val rentPrice: Double,

    @SerializedName("space_type")
    @Expose
    val spaceType: Int,

    @Expose
    var status: Int,

    @Expose
    val title: String

) : Serializable