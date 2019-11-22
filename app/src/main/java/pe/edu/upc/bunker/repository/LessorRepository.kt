package pe.edu.upc.bunker.repository

import pe.edu.upc.bunker.dto.LessorDTO
import pe.edu.upc.bunker.models.Lessor
import retrofit2.Call
import retrofit2.http.*

interface LessorRepository {
    @GET("api/v1/lessors/username/{id}")
    fun getLessorByUserId(
        @Path("id") id: Int,
        @Header("Authorization") authorization: String
    ): Call<Lessor>

    @POST("api/v1/lessors/")
    fun postLessor(
        @Header("Authorization") authorization: String, @Body lessor: LessorDTO
    ): Call<LessorDTO>

    @PUT("api/v1/lessors/{id}")
    fun updateLessor(@Header("Authorization") authorization: String, @Body lessor: Lessor): Call<Lessor>
}
