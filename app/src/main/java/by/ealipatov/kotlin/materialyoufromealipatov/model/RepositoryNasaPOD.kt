package by.ealipatov.kotlin.materialyoufromealipatov.model

import java.time.LocalDate

fun interface RepositoryNasaPOD {
    fun getUrlPicture(date: LocalDate, callback: CallbackPictureData)
}