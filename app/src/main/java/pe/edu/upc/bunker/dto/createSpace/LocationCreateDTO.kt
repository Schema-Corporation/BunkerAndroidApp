package pe.edu.upc.bunker.dto.createSpace


import com.google.gson.annotations.Expose

data class LocationCreateDTO(
    @Expose
    var address: String?= "",

    @Expose
    var latitude: Double?= 0.0,

    @Expose
    var longitude: Double?= 0.0
)