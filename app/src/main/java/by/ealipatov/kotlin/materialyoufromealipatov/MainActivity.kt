package by.ealipatov.kotlin.materialyoufromealipatov

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.ActivityMainBinding
import by.ealipatov.kotlin.materialyoufromealipatov.utils.*
import by.ealipatov.kotlin.materialyoufromealipatov.view.PictureOfTheDayFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        val sharedPreferences = this.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)

        //Если в файле настроек есть сохраненная тема - применяем ее.
        if(sharedPreferences.contains(THEME_KEY))
        setTheme(sharedPreferences.getInt(THEME_KEY,R.style.Theme_MaterialYouFromEalipatov))

        //Если в файле настроек есть выбор ночной/дневной/авто темы - применяем ее.
        if(sharedPreferences.contains(THEME_MODE_KEY)){
            when(sharedPreferences.getString(THEME_MODE_KEY, "Day")){
                "Day" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                "Night" ->AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                "Auto" ->AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
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
