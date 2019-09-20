package pe.edu.upc.bunker.repository

import pe.edu.upc.bunker.dto.LoginDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginRepository{

    @POST("/login")
    fun login(@Body login : LoginDTO,@Header("Authorization")token:String): Call<LoginDTO>
}