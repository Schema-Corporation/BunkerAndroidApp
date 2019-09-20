package pe.edu.upc.bunker.repository

import pe.edu.upc.bunker.dto.LoginDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginRepository {

    @POST("login")
    fun login(@Body login: LoginDTO): Call<LoginDTO>

    @POST("signup")
    fun signUp(@Body signUp: LoginDTO): Call<Void>
}