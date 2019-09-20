package pe.edu.upc.bunker.models


import com.google.gson.annotations.SerializedName

data class Lessor(
    @SerializedName("commercial_name")
    val commercialName: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("doc_number")
    val docNumber: Any,
    @SerializedName("doc_type")
    val docType: Int,
    val email: String,
    @SerializedName("first_name")
    val firstName: String,
    val id: Int,
    @SerializedName("last_name")
    val lastName: String,
    val phone: String,
    val ruc: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    val user: User
)