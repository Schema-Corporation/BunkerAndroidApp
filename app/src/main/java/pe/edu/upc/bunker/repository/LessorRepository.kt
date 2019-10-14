package pe.edu.upc.bunker.repository

import pe.edu.upc.bunker.models.Lessor
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface LessorRepository {
    @GET("api/v1/lessors/username/{id}")
    fun getLessorByUserId(@Path("id") id: Int,
                            @Header("Authorization") authorization: String): Call<Lessor>
}