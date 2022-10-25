package by.ealipatov.kotlin.materialyoufromealipatov.view.animation

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.Slide
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentAnimationSlideBinding

class AnimationSlideFragment: Fragment() {
    private var _binding: FragmentAnimationSlideBinding? = null
    private val binding: FragmentAnimationSlideBinding
        get() {
            return _binding!!
        }

    var flag: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimationSlideBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {
            flag = !flag
            val myAutoTransition = TransitionSet()
            //ORDERING_TOGETHER- одновременно; ORDERING_SEQUENTIAL - по очереди
            myAutoTransition.ordering = TransitionSet.ORDERING_TOGETHER
            //val fade = Fade()
            val slide = Slide(Gravity.BOTTOM)
            val changeBounds = ChangeBounds()
            myAutoTransition.addTransition(slide)
            slide.duration = 1000L
            myAutoTransition.addTransition(changeBounds)
            TransitionManager.beginDelayedTransition(binding.root, myAutoTransition)
            binding.text.visibility = if(flag) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}