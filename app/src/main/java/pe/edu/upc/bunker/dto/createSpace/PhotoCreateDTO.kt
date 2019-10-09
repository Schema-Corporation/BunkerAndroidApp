package pe.edu.upc.bunker.dto.createSpace


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PhotoCreateDTO(
    @SerializedName("photo_url")
    @Expose
    val photoUrl: String
)