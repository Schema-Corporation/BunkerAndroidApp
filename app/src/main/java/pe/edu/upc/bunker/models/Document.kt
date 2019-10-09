package pe.edu.upc.bunker.models


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Document(
    @SerializedName("document_type")
    @Expose
    val documentType: DocumentType,

    @Expose
    val id: Int,

    @SerializedName("url_document")
    @Expose
    val urlDocument: String
)