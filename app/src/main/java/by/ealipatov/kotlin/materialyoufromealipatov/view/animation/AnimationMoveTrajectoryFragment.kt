package by.ealipatov.kotlin.materialyoufromealipatov.view.animation

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.transition.ArcMotion
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentAnimationMoveTrajectoryBinding

class AnimationMoveTrajectoryFragment : Fragment() {
    private var _binding: FragmentAnimationMoveTrajectoryBinding? = null
    private val binding: FragmentAnimationMoveTrajectoryBinding
        get() {
            return _binding!!
        }

    var flag = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimationMoveTrajectoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.moveBtn.setOnClickListener {
            flag = !flag

            val params = it.layoutParams as FrameLayout.LayoutParams
            val changeBounds = ChangeBounds()
            val transitionSet = TransitionSet()
            changeBounds.duration = 1000L
            changeBounds.setPathMotion(ArcMotion())

            transitionSet.addTransition(changeBounds)
            TransitionManager.beginDelayedTransition(binding.root, transitionSet)
            if (flag) {
                params.gravity = Gravity.CENTER or Gravity.START
            } else {
                params.gravity = Gravity.BOTTOM or Gravity.END
            }
            it.layoutParams = params
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}