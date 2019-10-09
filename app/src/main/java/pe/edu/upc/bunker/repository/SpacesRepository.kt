package pe.edu.upc.bunker.repository

import pe.edu.upc.bunker.dto.SpaceInfoDTO
import pe.edu.upc.bunker.dto.createSpace.SpaceCreateDTO
import pe.edu.upc.bunker.models.Space
import retrofit2.Call
import retrofit2.http.*

interface SpacesRepository {

    @GET("api/v1/spaces/info_lessors/{id}")
    fun getSpacesByLessorId(@Path("id") id: Int,
                             @Header("Authorization") authorization: String): Call<List<SpaceInfoDTO>>


    @POST("api/v1/spaces/complete")
    fun postSpace(@Body spaceCreate: SpaceCreateDTO,
                  @Header("Authorization") authorization: String): Call<Space>
}