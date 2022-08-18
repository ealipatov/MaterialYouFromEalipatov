package by.ealipatov.kotlin.materialyoufromealipatov.viewmodel

import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.ealipatov.kotlin.materialyoufromealipatov.model.CallbackPictureData
import by.ealipatov.kotlin.materialyoufromealipatov.model.RepositoryNasaPODRetrofit
import by.ealipatov.kotlin.materialyoufromealipatov.model.ServerNasaPODResponseData
import java.io.IOException

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
        if (isConnection()) {
            repository = RepositoryNasaPODRetrofit()
        }
    }

    private fun isConnection(): Boolean {

        return true
    }
//    @Suppress("DEPRECATION")
//    fun isConnection(context: Context): Boolean {
//        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val activeNetworkInfo: NetworkInfo? = cm.activeNetworkInfo
//        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
//    }

    fun getPicture() {
        liveData.value = PODFragmentViewModelAppState.Loading
        repository.getUrlPicture(callback)
    }

    private val callback = object : CallbackPictureData {
        override fun onResponse(pictureData: ServerNasaPODResponseData) {
            if (isConnection())
            liveData.postValue(PODFragmentViewModelAppState.Success(pictureData))
        }

        override fun onFailure(e: IOException) {
            liveData.postValue(PODFragmentViewModelAppState.Error(e))
        }
    }
}
