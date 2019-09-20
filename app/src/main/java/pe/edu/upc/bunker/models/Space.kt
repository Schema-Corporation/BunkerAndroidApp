package pe.edu.upc.bunker.models


import com.google.gson.annotations.SerializedName

data class Space(
    val area: Int,
    @SerializedName("created_at")
    val createdAt: String,
    val height: Int,
    val id: Int,
    val lessor: Lessor,
    @SerializedName("rent_price")
    val rentPrice: Any,
    val status: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    val width: Int
)