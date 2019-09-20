package pe.edu.upc.bunker.models


import com.google.gson.annotations.SerializedName

data class BookingProcess(
    @SerializedName("created_at")
    val createdAt: String,
    val document: Document,
    @SerializedName("end_date")
    val endDate: String,
    val id: Int,
    val lessee: Lessee,
    @SerializedName("monthly_fee")
    val monthlyFee: Int,
    val space: Space,
    @SerializedName("start_date")
    val startDate: String,
    val step: Int,
    @SerializedName("updated_at")
    val updatedAt: String
)