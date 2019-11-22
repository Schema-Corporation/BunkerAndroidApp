package pe.edu.upc.bunker.repository

import pe.edu.upc.bunker.models.BookingProcess
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface BookingProcessRepository {
    @GET("/api/v1/booking_processes/lessees/{lessorId}")
    fun getSpacesNotification(
        @Path("lessorId") lessorId: Int, @Header("Authorization") authorization: String
    ): Call<List<BookingProcess>>
}