package by.ealipatov.kotlin.materialyoufromealipatov.model

import java.io.IOException

interface CallbackPictureData {
    fun onResponse(pictureData: ServerNasaPODResponseData)
    fun onFailure(e: IOException)
}