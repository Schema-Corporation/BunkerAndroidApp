package pe.edu.upc.bunker.dto.createSpace


import com.google.gson.annotations.Expose

data class LocationCreateDTO(
    @Expose
    val address: String,

    @Expose
    val latitude: String,

    @Expose
    val longitude: String
)