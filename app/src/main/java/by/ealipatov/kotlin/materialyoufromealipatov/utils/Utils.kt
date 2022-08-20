package by.ealipatov.kotlin.materialyoufromealipatov.utils

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