package by.ealipatov.kotlin.materialyoufromealipatov.view.animation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import androidx.fragment.app.Fragment
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentAnimationFabBinding
import by.ealipatov.kotlin.materialyoufromealipatov.utils.toast

class AnimationFABFragment : Fragment() {
    private var _binding: FragmentAnimationFabBinding? = null
    private val binding: FragmentAnimationFabBinding
        get() {
            return _binding!!
        }

    var flag: Boolean = false
    private val duration = 2000L

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
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionOneContainer, View.TRANSLATION_Y, -140f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionTwoContainer, View.TRANSLATION_Y, -70f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.transparentBackground, View.ALPHA, 0.5f)
                    .setDuration(duration).start()

                binding.optionOneContainer.animate()
                    .alpha(1f)
                    .setDuration(duration)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            binding.optionOneContainer.isClickable = true
                            binding.optionOneContainer.setOnClickListener {
                                toast("Option 1")
                            }
                        }
                    })

                binding.optionTwoContainer.animate()
                    .alpha(1f)
                    .setDuration(duration)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            binding.optionTwoContainer.isClickable = true
                            binding.optionTwoContainer.setOnClickListener {
                                toast("Option 2")
                            }
                        }
                    })

                binding.optionTwoContainer.visibility = View.VISIBLE
            } else {
                ObjectAnimator.ofFloat(binding.plusImageview, View.ROTATION, 675f, 0f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionOneContainer, View.TRANSLATION_Y, 0f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionTwoContainer, View.TRANSLATION_Y, 0f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.transparentBackground, View.ALPHA, 0f)
                    .setDuration(duration).start()

                binding.optionOneContainer.animate()
                    .alpha(0f)
                    .setDuration(duration)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            binding.optionOneContainer.isClickable = false
                            binding.optionOneContainer.setOnClickListener {
                            }
                        }
                    })

                binding.optionTwoContainer.animate()
                    .alpha(0f)
                    .setDuration(duration)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            binding.optionTwoContainer.isClickable = false
                            binding.optionTwoContainer.setOnClickListener {
                            }
                        }
                    })
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

