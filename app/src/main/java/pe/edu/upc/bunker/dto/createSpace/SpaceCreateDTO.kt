package pe.edu.upc.bunker.dto.createSpace


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SpaceCreateDTO(
    @Expose
    val area: Int,

    @Expose
    val description: String,

    @Expose
    val height: Int,

    @Expose
    val lessor: LessorCreateDTO,

    @Expose
    val location: LocationCreateDTO,

    @Expose
    val photos: List<PhotoCreateDTO>,

    @SerializedName("rent_price")
    @Expose
    val rentPrice: Int,

    @Expose
    val services: List<ServiceCreateDTO>,

    @SerializedName("space_type")
    @Expose
    val spaceType: Int,

    @Expose
    val title: String,

    @Expose
    val width: Int
)