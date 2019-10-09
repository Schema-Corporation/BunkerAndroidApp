package pe.edu.upc.bunker.models


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DocumentType(
    @SerializedName("created_at")
    @Expose
    val createdAt: String,

    @Expose
    val description: String,

    @Expose
    val id: Int,

    @Expose
    val name: String,

    @SerializedName("updated_at")
    @Expose
    val updatedAt: String
)