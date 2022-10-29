package by.ealipatov.kotlin.materialyoufromealipatov.view.animation


import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import by.ealipatov.kotlin.materialyoufromealipatov.R
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentAnimationConstraintSetBinding

class AnimationConstraintSetFragment : Fragment() {

    private var _binding: FragmentAnimationConstraintSetBinding? = null
    private val binding: FragmentAnimationConstraintSetBinding
        get() {
            return _binding!!
        }

    private var flag = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimationConstraintSetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val constraintSet = ConstraintSet()

        constraintSet.clone(requireContext(), R.layout.fragment_animation_constraint_set)

        binding.tap.setOnClickListener {
            flag = !flag

            val changeBounds = ChangeBounds()
            changeBounds.duration = 1000L
            changeBounds.interpolator = AnticipateOvershootInterpolator(5.0f)
            TransitionManager.beginDelayedTransition(binding.containerConstraint, changeBounds)
            if (flag) {
                constraintSet.connect(
                    R.id.title,
                    ConstraintSet.RIGHT,
                    R.id.backgroundImage,
                    ConstraintSet.RIGHT
                )

                constraintSet.clear(R.id.date, ConstraintSet.BOTTOM)
                constraintSet.connect(
                    R.id.date,
                    ConstraintSet.TOP,
                    R.id.title,
                    ConstraintSet.BOTTOM
                )

                constraintSet.clear(R.id.description, ConstraintSet.TOP)
                constraintSet.connect(
                    R.id.description,
                    ConstraintSet.BOTTOM,
                    R.id.backgroundImage,
                    ConstraintSet.BOTTOM
                )

                constraintSet.applyTo(binding.containerConstraint)

            } else {
                constraintSet.connect(
                    R.id.title,
                    ConstraintSet.RIGHT,
                    R.id.backgroundImage,
                    ConstraintSet.LEFT
                )

                constraintSet.clear(R.id.date, ConstraintSet.TOP)
                constraintSet.connect(
                    R.id.date,
                    ConstraintSet.BOTTOM,
                    R.id.title,
                    ConstraintSet.BOTTOM
                )

                constraintSet.clear(R.id.description, ConstraintSet.BOTTOM)
                constraintSet.connect(
                    R.id.description,
                    ConstraintSet.TOP,
                    R.id.backgroundImage,
                    ConstraintSet.BOTTOM
                )

                constraintSet.applyTo(binding.containerConstraint)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}