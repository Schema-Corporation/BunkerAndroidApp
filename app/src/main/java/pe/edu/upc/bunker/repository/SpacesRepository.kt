package pe.edu.upc.bunker.repository

import pe.edu.upc.bunker.dto.SpaceDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface SpacesRepository {

    @GET("api/v1/spaces/info_lessors/{id}")
    fun getSpacesByLessorId(@Path("id") id: Int,
                             @Header("Authorization") authorization: String): Call<List<SpaceDTO>>


}