package pe.edu.upc.bunker.models


import com.google.gson.annotations.SerializedName

data class Document(
    @SerializedName("document_type")
    val documentType: DocumentType,
    val id: Int,
    @SerializedName("url_document")
    val urlDocument: String
)