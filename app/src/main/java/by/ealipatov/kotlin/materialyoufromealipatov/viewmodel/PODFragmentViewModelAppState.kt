package by.ealipatov.kotlin.materialyoufromealipatov.viewmodel

import by.ealipatov.kotlin.materialyoufromealipatov.model.ServerNasaPODResponseData

sealed class PODFragmentViewModelAppState {
    data class Success(val pictureData: ServerNasaPODResponseData) : PODFragmentViewModelAppState()
    data class Error(val error: Throwable) : PODFragmentViewModelAppState()
    object Loading : PODFragmentViewModelAppState()
    //data class Loading(val progress: Int?) : PODFragmentViewModelAppState()
}