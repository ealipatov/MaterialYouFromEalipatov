package by.ealipatov.kotlin.materialyoufromealipatov.model

import android.os.Build
import androidx.annotation.RequiresApi
import by.ealipatov.kotlin.materialyoufromealipatov.BuildConfig
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class RepositoryNasaPODRetrofit: RepositoryNasaPOD {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun getUrlPicture(date: LocalDate, callback: CallbackPictureData) {
        val retrofitImpl = Retrofit.Builder()
        retrofitImpl.baseUrl("https://api.nasa.gov/")
        retrofitImpl.addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        val api = retrofitImpl.build().create(RepositoryNasaDODApi::class.java)
        api.getPictureOfTheDay(BuildConfig.NASA_API_KEY,date.toString()).enqueue(object :
            Callback<ServerNasaPODResponseData> {
            override fun onResponse(call: Call<ServerNasaPODResponseData>, response: Response<ServerNasaPODResponseData>) {
                if(response.isSuccessful&&response.body()!=null){
                    callback.onResponse(response.body()!!)
                }else {
                    callback.onFailure(IOException("403 404"))
                }
            }
            override fun onFailure(call: Call<ServerNasaPODResponseData>, t: Throwable) {
                callback.onFailure(t as IOException)
            }
        })
    }

}