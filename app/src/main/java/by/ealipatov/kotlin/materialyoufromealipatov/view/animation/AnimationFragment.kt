package by.ealipatov.kotlin.materialyoufromealipatov.view.animation

import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentAnimationBinding

class AnimationFragment: Fragment() {
    private var _binding: FragmentAnimationBinding? = null
    private val binding: FragmentAnimationBinding
        get() {
            return _binding!!
        }

    var flag: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {
            flag = !flag

            TransitionManager.beginDelayedTransition(binding.root)
            binding.text.visibility = if(flag) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}