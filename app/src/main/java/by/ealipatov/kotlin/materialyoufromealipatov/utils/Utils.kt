package by.ealipatov.kotlin.materialyoufromealipatov.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.Gravity
import android.widget.Toast
import androidx.fragment.app.Fragment

/***
 * Функция выывода на экран сообщений - "тостов"
 * На вход принимает строку
 */
fun Fragment.toast(string: String?) {
    Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
        setGravity(Gravity.BOTTOM, 0, 250)
        show()
    }
}

/***
 * Функция проверяет наличие подключения к интернету
 */
    @Suppress("DEPRECATION")
    fun isConnection(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo: NetworkInfo? = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }