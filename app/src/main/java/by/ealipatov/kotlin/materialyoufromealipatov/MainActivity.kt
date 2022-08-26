package by.ealipatov.kotlin.materialyoufromealipatov


import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.ActivityMainBinding
import by.ealipatov.kotlin.materialyoufromealipatov.utils.SHARED_PREF_FILE
import by.ealipatov.kotlin.materialyoufromealipatov.utils.THEME_KEY
import by.ealipatov.kotlin.materialyoufromealipatov.view.PictureOfTheDayFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance()).commit()
        }
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
        if(savedInstanceState != null){
            setTheme(sharedPreferences.getInt(THEME_KEY,R.style.Theme_MaterialYouFromEalipatov))
        }

        //Ичпользуем системную тему
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.getDefaultNightMode())
        setTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

}