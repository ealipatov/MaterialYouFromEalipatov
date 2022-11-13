package by.ealipatov.kotlin.materialyoufromealipatov

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.ActivitySplashBinding


class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val rotateValue = 750f
    private val interpolatorDuration = 10000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView.animate().rotationBy(rotateValue)
            .setInterpolator(AccelerateDecelerateInterpolator()).setDuration(interpolatorDuration)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    //Nothing to do
                }

                override fun onAnimationEnd(animation: Animator) {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }

                override fun onAnimationCancel(animation: Animator) {
                    //Nothing to do
                }

                override fun onAnimationRepeat(animation: Animator) {
                    //Nothing to do
                }
            })
    }
}
