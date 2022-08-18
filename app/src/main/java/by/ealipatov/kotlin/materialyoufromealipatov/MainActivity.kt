package by.ealipatov.kotlin.materialyoufromealipatov


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.ActivityMainBinding
import by.ealipatov.kotlin.materialyoufromealipatov.view.PictureOfTheDayFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment()).commit()
//                .replace(R.id.container, PictureOfTheDayFragment.newInstance()).commit()
        }

    }
}