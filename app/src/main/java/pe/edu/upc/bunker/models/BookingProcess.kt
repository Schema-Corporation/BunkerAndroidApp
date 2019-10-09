package pe.edu.upc.bunker.models


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BookingProcess(
    @SerializedName("created_at")
    @Expose
    val createdAt: String,

    @Expose
    val document: Document,

    @SerializedName("end_date")
    @Expose
    val endDate: String,

    @Expose
    val id: Int,

    @Expose
    val lessee: Lessee,

    @SerializedName("monthly_fee")
    @Expose
    val monthlyFee: Int,

    @Expose
    val space: Space,

    @SerializedName("start_date")
    @Expose
    val startDate: String,

    @Expose
    val step: Int,

    @SerializedName("updated_at")
    @Expose
    val updatedAt: String
)