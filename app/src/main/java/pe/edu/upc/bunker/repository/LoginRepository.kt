package pe.edu.upc.bunker.repository

import pe.edu.upc.bunker.dto.LoginDTO
import pe.edu.upc.bunker.dto.LoginResponseDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginRepository {

    @POST("login")
    fun login(@Body login: LoginDTO): Call<LoginResponseDTO>

    @POST("signup")
    fun signUp(@Body signUp: LoginDTO): Call<Void>

    @DELETE("logout")
    fun logout(@Header("Authorization") authorization: String): Call<Void>
}