package by.ealipatov.kotlin.materialyoufromealipatov.view.animation

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentAnimationFabBinding

class AnimationFABFragment: Fragment() {
    private var _binding: FragmentAnimationFabBinding? = null
    private val binding: FragmentAnimationFabBinding
        get() {
            return _binding!!
        }

    var flag: Boolean = false
    val duration = 2000L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimationFabBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            flag = !flag

            if (flag) {
                ObjectAnimator.ofFloat(binding.plusImageview, View.ROTATION, 0f, 675f)
                    .setDuration(duration)
                    .start()
            } else {
                ObjectAnimator.ofFloat(binding.plusImageview, View.ROTATION, 675f, 0f)
                    .setDuration(duration)
                    .start()
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}