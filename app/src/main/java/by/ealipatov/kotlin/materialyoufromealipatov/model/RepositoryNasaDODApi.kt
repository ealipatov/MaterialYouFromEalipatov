package by.ealipatov.kotlin.materialyoufromealipatov.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RepositoryNasaDODApi {

    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String): Call<ServerNasaPODResponseData>
}