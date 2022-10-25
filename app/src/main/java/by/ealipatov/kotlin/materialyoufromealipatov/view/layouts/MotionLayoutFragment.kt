package by.ealipatov.kotlin.materialyoufromealipatov.view.layouts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.ealipatov.kotlin.materialyoufromealipatov.R
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentMotionLayoutBinding
import by.ealipatov.kotlin.materialyoufromealipatov.view.animation.*
import kotlinx.android.synthetic.main.fragment_motion_layout.*

class MotionLayoutFragment: Fragment(), View.OnClickListener {

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
        btn_anime_text.setOnClickListener(this)
        btn_anime_explode.setOnClickListener(this)
        btn_anime_image.setOnClickListener(this)
        btn_anime_move_trajectory.setOnClickListener(this)
        btn_anime_mix.setOnClickListener(this)
        btn_anime_animator.setOnClickListener(this)
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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_anime_text -> toFragment(AnimationSlideFragment())
            R.id.btn_anime_explode -> toFragment(AnimationExplodeFragment())
            R.id.btn_anime_image -> toFragment(AnimationImageFragment())
            R.id.btn_anime_move_trajectory -> toFragment(AnimationMoveTrajectoryFragment())
            R.id.btn_anime_mix -> toFragment(AnimationMixFragment())
            R.id.btn_anime_animator -> toFragment(AnimationFABFragment())
        }
    }
}