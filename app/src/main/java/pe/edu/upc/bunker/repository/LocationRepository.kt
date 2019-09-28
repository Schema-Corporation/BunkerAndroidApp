package pe.edu.upc.bunker.repository

import pe.edu.upc.bunker.models.Location
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface LocationRepository {

    @GET("api/v1/locations/spaces/{id}")
    fun getLocationBySpaceId(@Path("id") id: Int,
               @Header("Authorization") authorization: String): Call<Location>

}