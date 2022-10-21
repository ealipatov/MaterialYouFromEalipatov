package by.ealipatov.kotlin.materialyoufromealipatov.view.layouts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.ealipatov.kotlin.materialyoufromealipatov.R
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentMotionLayoutBinding
import by.ealipatov.kotlin.materialyoufromealipatov.view.animation.AnimationFragment

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

        binding.btnLeft.setOnClickListener {
            parentFragmentManager.apply {
                beginTransaction()
                    .add(R.id.container, AnimationFragment())
                    .commit()
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}