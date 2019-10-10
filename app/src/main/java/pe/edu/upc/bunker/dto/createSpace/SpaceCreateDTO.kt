package pe.edu.upc.bunker.dto.createSpace


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SpaceCreateDTO(
    @Expose
    var area: Double?= 0.0,

    @Expose
    var description: String?= "",

    @Expose
    var height: Double?= 0.0,

    @Expose
    var lessor: LessorCreateDTO?= LessorCreateDTO(),

    @Expose
    var location: LocationCreateDTO?= LocationCreateDTO(),

    @Expose
    var photos: List<PhotoCreateDTO>?= ArrayList<PhotoCreateDTO>(),

    @SerializedName("rent_price")
    @Expose
    var rentPrice: Double?= 0.0,

    @Expose
    var services: List<ServiceCreateDTO>?= ArrayList<ServiceCreateDTO>(),

    @SerializedName("space_type")
    @Expose
    var spaceType: Int?= 0,

    @Expose
    var title: String?= "",

    @Expose
    var width: Double?= 0.0
)