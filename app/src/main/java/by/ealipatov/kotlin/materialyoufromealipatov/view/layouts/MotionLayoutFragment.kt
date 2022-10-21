package by.ealipatov.kotlin.materialyoufromealipatov.view.layouts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.ealipatov.kotlin.materialyoufromealipatov.R
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentMotionLayoutBinding
import by.ealipatov.kotlin.materialyoufromealipatov.view.animation.AnimationExplodeFragment
import by.ealipatov.kotlin.materialyoufromealipatov.view.animation.AnimationFragment
import by.ealipatov.kotlin.materialyoufromealipatov.view.animation.AnimationImageFragment

class MotionLayoutFragment: Fragment() {

    private var _binding: FragmentMotionLayoutBinding? = null
    private val binding: FragmentMotionLayoutBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMotionLayoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAnimeText.setOnClickListener {
            toFragment(AnimationFragment())
        }
        binding.btnAnimeExplode.setOnClickListener {
            toFragment(AnimationExplodeFragment())
        }
        binding.btnAnimeImage.setOnClickListener {
            toFragment(AnimationImageFragment())
        }
    }

    private fun toFragment(fragment: Fragment) {
        parentFragmentManager.apply {
            beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}