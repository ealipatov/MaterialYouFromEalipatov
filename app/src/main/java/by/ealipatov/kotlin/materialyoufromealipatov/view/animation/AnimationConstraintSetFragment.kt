package by.ealipatov.kotlin.materialyoufromealipatov.view.animation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import by.ealipatov.kotlin.materialyoufromealipatov.R
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentAnimationStartBinding

class AnimationConstraintSetFragment: Fragment(){

    private var _binding: FragmentAnimationStartBinding? = null
    private val binding: FragmentAnimationStartBinding
        get() {
            return _binding!!
        }

    var flag = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimationStartBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val constraintSetStart = ConstraintSet()
        val constraintSetEnd = ConstraintSet()
       // constraintSetStart.clone(binding.constraintContainer)
        constraintSetStart.clone(requireContext(), R.layout.fragment_animation_start)
        constraintSetEnd.clone(requireContext(), R.layout.fragment_animation_end)

        binding.tap.setOnClickListener{
            flag = !flag
            if(flag){
                constraintSetEnd.applyTo(binding.constraintContainer)
            } else {
                constraintSetStart.applyTo(binding.constraintContainer)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}