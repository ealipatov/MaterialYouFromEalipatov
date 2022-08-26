package by.ealipatov.kotlin.materialyoufromealipatov


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.ActivityMainBinding
import by.ealipatov.kotlin.materialyoufromealipatov.utils.SELECTED_THEME
import by.ealipatov.kotlin.materialyoufromealipatov.utils.SHARED_PREF_FILE
import by.ealipatov.kotlin.materialyoufromealipatov.utils.THEME_KEY
import by.ealipatov.kotlin.materialyoufromealipatov.view.PictureOfTheDayFragment


class MainActivity : AppCompatActivity() {

    private var themeID = R.style.Theme_MaterialYouFromEalipatov

    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance()).commit()
        }
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
        if(sharedPreferences.getBoolean(SHARED_PREF_FILE, false)) {
            themeID = sharedPreferences.getInt(THEME_KEY,R.style.Theme_MaterialYouFromEalipatov)
        }

        setTheme(themeID)

        //Ичпользуем системную тему
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.getDefaultNightMode())
        setTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outState.putInt(SELECTED_THEME, themeID)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        themeID = savedInstanceState.getInt(SELECTED_THEME)
    }
}
