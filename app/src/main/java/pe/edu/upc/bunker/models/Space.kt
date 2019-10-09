package pe.edu.upc.bunker.models


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Space(
    @Expose
    val area: Int,

    @SerializedName("created_at")
    @Expose
    val createdAt: String,

    @Expose
    val height: Int,

    @Expose
    val id: Int,

    @Expose
    val lessor: Lessor,

    @SerializedName("rent_price")
    @Expose
    val rentPrice: Any,

    @Expose
    val status: Int,

    @SerializedName("updated_at")
    @Expose
    val updatedAt: String,

    @Expose
    val width: Int,

    @Expose
    val title: String,

    @Expose
    val description: String,

    @SerializedName("space_type")
    @Expose
    val spaceType: Int,

    @Expose
    val location: Location
)

