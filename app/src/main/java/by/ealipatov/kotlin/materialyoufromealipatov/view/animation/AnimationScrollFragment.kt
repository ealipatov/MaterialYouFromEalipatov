package by.ealipatov.kotlin.materialyoufromealipatov.view.animation

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentAnimationScrollBinding

class AnimationScrollFragment : Fragment() {
    private var _binding: FragmentAnimationScrollBinding? = null
    private val binding: FragmentAnimationScrollBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimationScrollBinding.inflate(inflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.scrollView.setOnScrollChangeListener {_, _, _, _, _ ->
            binding.toolbar.isSelected =  binding.scrollView.canScrollVertically(-1)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

