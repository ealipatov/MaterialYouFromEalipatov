package by.ealipatov.kotlin.materialyoufromealipatov.view.layouts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentConstraintLayoutBinding

class ConstraintLayoutFragment: Fragment() {

    private var flag = false
    private var _binding: FragmentConstraintLayoutBinding? = null
    private val binding: FragmentConstraintLayoutBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConstraintLayoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGroupOnOff.setOnClickListener {
            flag = !flag
            binding.groupBtn.visibility = if (flag) View.GONE else View.VISIBLE
        }

    }
}