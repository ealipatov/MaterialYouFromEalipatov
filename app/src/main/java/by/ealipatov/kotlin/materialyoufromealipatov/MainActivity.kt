package by.ealipatov.kotlin.materialyoufromealipatov

import android.animation.ObjectAnimator
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.ActivityMainBinding
import by.ealipatov.kotlin.materialyoufromealipatov.utils.*
import by.ealipatov.kotlin.materialyoufromealipatov.view.SettingFragment
import by.ealipatov.kotlin.materialyoufromealipatov.view.solarSystem.ViewPagerFragment
import by.ealipatov.kotlin.materialyoufromealipatov.view.layouts.LayoutsViewPagerFragment
import by.ealipatov.kotlin.materialyoufromealipatov.view.pictureOfTheDay.PictureOfTheDayViewPagerFragment

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.bottom_navigation_container, PictureOfTheDayViewPagerFragment())
                .commit()
        }

        var isHideSplashScreen = false

        object : CountDownTimer(TIMER, TIMER_INTERVAL){
            override fun onTick(millisUntilFinished: Long) {
                //Nothing to do
            }

            override fun onFinish() {
                isHideSplashScreen = true
            }
        }.start()

        val content : View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (isHideSplashScreen) {
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        false
                    }
                }
            })

        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val slideLeft = ObjectAnimator.ofFloat(
                splashScreenView,
                View.TRANSLATION_X,
                0f,
                -splashScreenView.height.toFloat()
            )
            slideLeft.interpolator = AnticipateInterpolator()
            slideLeft.duration = 1000L

            slideLeft.doOnEnd { splashScreenView.remove() }
            slideLeft.start()
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
        //включим ресивер
        registerReceiver(configChangeReceiver, filter)

        val sharedPreferences = this.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)

        //Если в файле настроек есть сохраненная тема - применяем ее.
        if (sharedPreferences.contains(THEME_KEY))
            setTheme(sharedPreferences.getInt(THEME_KEY, R.style.AppTheme))

        //Если в файле настроек есть выбор ночной/дневной/авто/системной темы - применяем ее.
        if (sharedPreferences.contains(THEME_MODE_KEY)) {
            sharedPreferences.getString(THEME_MODE_KEY, "System")?.let { changeMode(it) }
        }

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.app_bar_telescope -> {
                    toFragment(PictureOfTheDayViewPagerFragment())
                    true
                }
                R.id.app_bar_system -> {
                    toFragment(ViewPagerFragment())
                    true
                }
                R.id.app_bar_layouts -> {
                    toFragment(LayoutsViewPagerFragment())
                    true
                }
                R.id.app_bar_settings -> {
                    toFragment(SettingFragment())
                    true
                }
                else -> false
            }
        }
        //Количество сообщений в углу иконки
        val badge = binding.bottomNavigationView.getOrCreateBadge(R.id.app_bar_telescope)
        badge.number = 10
    }

    private fun toFragment(fragment: Fragment) {
        supportFragmentManager.apply {
            beginTransaction()
                .replace(R.id.bottom_navigation_container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    /***
     * Функция изменения режима день/ночь/авто/системый
     * На вход принимает строку
     */
    fun changeMode(mode: String) {
        when (mode) {
            "Day" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "Night" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            "Auto" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
            "System" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}
