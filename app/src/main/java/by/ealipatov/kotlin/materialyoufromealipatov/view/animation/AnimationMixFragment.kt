package by.ealipatov.kotlin.materialyoufromealipatov.view.animation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.transition.TransitionManager
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentAnimationMixBinding

class AnimationMixFragment: Fragment() {
    private var _binding: FragmentAnimationMixBinding? = null
    private val binding: FragmentAnimationMixBinding
        get() {
            return _binding!!
        }

    var flag = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimationMixBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titles: MutableList<String> = ArrayList()
        for (i in 0..4){
            titles.add("Item $i")
        }

        binding.btnMix.setOnClickListener {
            flag = !flag
            TransitionManager.beginDelayedTransition(binding.root)
            binding.mixContainer.removeAllViews()
            titles.shuffle()
            titles.forEach{
                binding.mixContainer.addView(TextView(requireContext()).apply {
                    text = it
                    //Присвоим псевдонимы
                    ViewCompat.setTransitionName(this, it)
                })
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}