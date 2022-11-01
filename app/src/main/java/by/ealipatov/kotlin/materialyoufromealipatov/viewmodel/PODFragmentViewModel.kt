package by.ealipatov.kotlin.materialyoufromealipatov.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.ealipatov.kotlin.materialyoufromealipatov.model.CallbackPictureData
import by.ealipatov.kotlin.materialyoufromealipatov.model.RepositoryNasaPODRetrofit
import by.ealipatov.kotlin.materialyoufromealipatov.model.ServerNasaPODResponseData
import java.io.IOException
import java.time.LocalDate

class PODFragmentViewModel(
    private val liveData: MutableLiveData<PODFragmentViewModelAppState> =
        MutableLiveData<PODFragmentViewModelAppState>()
) : ViewModel() {

    lateinit var repository: RepositoryNasaPODRetrofit

    fun getLiveData(): MutableLiveData<PODFragmentViewModelAppState> {
        choiceRepository()
        return liveData
    }

    private fun choiceRepository() {
            repository = RepositoryNasaPODRetrofit()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getPicture(date: LocalDate) {
        liveData.value = PODFragmentViewModelAppState.Loading
        repository.getUrlPicture(date, callback)
    }

    private val callback = object : CallbackPictureData {
        override fun onResponse(pictureData: ServerNasaPODResponseData) {
            liveData.postValue(PODFragmentViewModelAppState.Success(pictureData))
        }

        override fun onFailure(e: IOException) {
            liveData.postValue(PODFragmentViewModelAppState.Error(e))
        }
    }
}
