package by.ealipatov.kotlin.materialyoufromealipatov

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
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
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commit()
        }

        //Настроим ресивер для мониторинга изменения конфигурации темного/светлого режима
        val filter = IntentFilter(Intent.ACTION_CONFIGURATION_CHANGED)
        val configChangeReceiver: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                    Configuration.UI_MODE_NIGHT_NO -> {
                        changeMode("Night")
                        recreate()
                    }
                    Configuration.UI_MODE_NIGHT_YES -> {
                        changeMode("Day")
                        recreate()
                    }
                }
            }
        }
        //включим ресимвер
        registerReceiver(configChangeReceiver, filter)

        val sharedPreferences = this.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        //Если в файле настроек есть сохраненная тема - применяем ее.
        if(sharedPreferences.contains(THEME_KEY))
            setTheme(sharedPreferences.getInt(THEME_KEY,R.style.AppTheme))

        //Если в файле настроек есть выбор ночной/дневной/авто/системной темы - применяем ее.
        if(sharedPreferences.contains(THEME_MODE_KEY)){
            sharedPreferences.getString(THEME_MODE_KEY, "System")?.let { changeMode(it) }
        }
    }

    /***
     * Функция изменения режима день/ночь/авто/системый
     * На вход принимает строку
     */
    private fun changeMode (mode: String){
        when(mode){
            "Day" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "Night" ->AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            "Auto" ->AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
            "System" ->AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}
