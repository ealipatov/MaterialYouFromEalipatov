package by.ealipatov.kotlin.materialyoufromealipatov

import by.ealipatov.kotlin.materialyoufromealipatov.model.CallbackPictureData
import by.ealipatov.kotlin.materialyoufromealipatov.model.RepositoryNasaPODRetrofit
import by.ealipatov.kotlin.materialyoufromealipatov.model.ServerNasaPODResponseData
import kotlinx.coroutines.*
import org.junit.Test

import org.junit.Assert.*
import java.io.IOException
import java.time.LocalDate

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class TestRetrofit{

    //Диспатчер потоков IO для работы с вводом/выводом данных (не работает с главным потоком)
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var job: Job? = null
    var check = ""
    val repository = RepositoryNasaPODRetrofit()
    val callback = object : CallbackPictureData {
        override fun onResponse(pictureData: ServerNasaPODResponseData) {
            check = "ok"
        }
        override fun onFailure(e: IOException) {
        }
    }

    @Test
    fun answers_isCorrect() {
        job = scope.launch{ //isActive - проверяет статус корутины
            while (isActive) {
                repository.getUrlPicture(LocalDate.now(),callback)
                assertEquals("ok", check)
            }
        }
        scope.cancel()
    }
}