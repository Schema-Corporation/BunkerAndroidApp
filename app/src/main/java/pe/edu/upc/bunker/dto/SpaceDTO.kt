package pe.edu.upc.bunker.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import pe.edu.upc.bunker.models.Lessor
import java.io.Serializable

class SpaceDTO : Serializable {
    @Expose
    var area: Int ?= null
    @SerializedName("created_at")
    val createdAt: String?= null
    val height: Int = 0
    val id: Int = 0
    val lessor: Lessor? = null
    @SerializedName("rent_price")
    val rentPrice: Double = 0.0
    val status: Int = 0
    @SerializedName("updated_at")
    val updatedAt: String? = null
    val width: Int = 0
    val title: String? = null
    val description: String? = null
    @SerializedName("space_type")
    val spaceType: Int = 0
}