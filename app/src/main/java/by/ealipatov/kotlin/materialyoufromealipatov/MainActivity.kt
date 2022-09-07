package by.ealipatov.kotlin.materialyoufromealipatov

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.ActivityMainBinding
import by.ealipatov.kotlin.materialyoufromealipatov.utils.AUTO_NIGHT_MODE_KEY
import by.ealipatov.kotlin.materialyoufromealipatov.utils.NIGHT_MODE_KEY
import by.ealipatov.kotlin.materialyoufromealipatov.utils.SHARED_PREF_FILE
import by.ealipatov.kotlin.materialyoufromealipatov.utils.THEME_KEY
import by.ealipatov.kotlin.materialyoufromealipatov.view.PictureOfTheDayFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        val sharedPreferences = this.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)

        //Если в файле настроек есть сохраненная тема - применяем ее.
        if(sharedPreferences.contains(THEME_KEY))
        setTheme(sharedPreferences.getInt(THEME_KEY,R.style.Theme_MaterialYouFromEalipatov))


        //Если в файле настроек есть выбор ночной/дневной темы - применяем ее.
        if(sharedPreferences.contains(NIGHT_MODE_KEY)){
            when(sharedPreferences.getBoolean(NIGHT_MODE_KEY, false)){
                true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                false ->AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        //Если в файле настроек есть выбор авто ночной/дневной темы - применяем ее.
        if(sharedPreferences.contains(AUTO_NIGHT_MODE_KEY)){
            if(sharedPreferences.getBoolean(NIGHT_MODE_KEY, false)){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
            }
        }

        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commit()
        }
    }
}
