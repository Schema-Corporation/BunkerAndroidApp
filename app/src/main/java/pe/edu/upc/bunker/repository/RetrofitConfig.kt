package pe.edu.upc.bunker.repository

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClientInstance {
    companion object {
        private val BASE_URL = "https://bunker-253200.appspot.com/"
        private lateinit var retrofit : Retrofit
    }

    fun getRetrofitInstance() : Retrofit {

        val builder = GsonBuilder()
        builder.excludeFieldsWithoutExposeAnnotation()
        val gson = builder.create()

        retrofit = retrofit2.Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit
    }
}