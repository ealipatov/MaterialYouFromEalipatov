package by.ealipatov.kotlin.materialyoufromealipatov.view.layouts

import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import by.ealipatov.kotlin.materialyoufromealipatov.R
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentMotionLayoutBinding
import by.ealipatov.kotlin.materialyoufromealipatov.view.animation.*

class MotionLayoutFragment : Fragment() {

    private var _binding: FragmentMotionLayoutBinding? = null
    private val binding: FragmentMotionLayoutBinding
        get() {
            return _binding!!
        }

    private fun showPopup(view: View) {
        val popup = PopupMenu(requireContext(), view)
        popup.inflate(R.menu.menu_animation)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.slide -> {
                    toFragment(AnimationSlideFragment())
                    true
                }
                R.id.explode -> {
                    toFragment(AnimationExplodeFragment())
                    true
                }
                R.id.image -> {
                    toFragment(AnimationImageFragment())
                    true
                }
                R.id.move -> {
                    toFragment(AnimationMoveTrajectoryFragment())
                    true
                }
                R.id.mix -> {
                    toFragment(AnimationMixFragment())
                    true
                }
                R.id.animator -> {
                    toFragment(AnimationFABFragment())
                    true
                }
                R.id.scroll -> {
                    toFragment(AnimationScrollFragment())
                    true
                }
                R.id.motion -> {
                    toFragment(AnimationMotionFragment())
                    true
                }
                else -> {
                    return@setOnMenuItemClickListener false
                }
            }
        }
        popup.show()
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

        binding.toolbar.setOnClickListener {
            showPopup(it)
        }
    }

    private fun toFragment(fragment: Fragment) {
        childFragmentManager.apply {
            beginTransaction()
                .replace(R.id.container, fragment, tag)
                .commitAllowingStateLoss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}