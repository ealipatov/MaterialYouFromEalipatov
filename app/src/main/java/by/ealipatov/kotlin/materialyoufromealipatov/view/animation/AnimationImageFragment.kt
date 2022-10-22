package by.ealipatov.kotlin.materialyoufromealipatov.view.animation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentAnimationImageBinding
import kotlinx.android.synthetic.main.fragment_animation_image.view.*

class AnimationImageFragment: Fragment() {

    private var _binding: FragmentAnimationImageBinding? = null
    private val binding: FragmentAnimationImageBinding
        get() {
            return _binding!!
        }

    var flag = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimationImageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageView.setOnClickListener{
            flag = !flag

            val params = it.layoutParams as LinearLayout.LayoutParams
            val changeImageTransform = ChangeImageTransform()
            val changeBounds = ChangeBounds()
            val transitionSet = TransitionSet()
            changeBounds.duration = 1000L

            transitionSet.addTransition(changeBounds)
            transitionSet.addTransition(changeImageTransform)

            TransitionManager.beginDelayedTransition(binding.root,transitionSet)

            if (flag){
                params.height = LinearLayout.LayoutParams.MATCH_PARENT
            //    params.weight = LinearLayout.LayoutParams.WRAP_CONTENT.toFloat()
                binding.imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            } else{
                params.height = LinearLayout.LayoutParams.WRAP_CONTENT
                binding.imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
            }
            it.image_view.layoutParams = params

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}