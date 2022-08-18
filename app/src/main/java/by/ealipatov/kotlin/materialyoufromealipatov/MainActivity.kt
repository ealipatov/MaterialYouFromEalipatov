package by.ealipatov.kotlin.materialyoufromealipatov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.ealipatov.kotlin.materialyoufromealipatov.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}