package by.ealipatov.kotlin.materialyoufromealipatov.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.ealipatov.kotlin.materialyoufromealipatov.R
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentChipsBinding
import by.ealipatov.kotlin.materialyoufromealipatov.utils.toast
import com.google.android.material.chip.Chip

class ChipsFragment : Fragment() {

    private var _binding: FragmentChipsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChipsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.chipGroup.setOnCheckedStateChangeListener { chipGroup, position ->
            chipGroup.findViewById<Chip>(position[0])?.let {
                toast(getString(R.string.selected) + "${it.text}")
            }
        }

        binding.chipClose.setOnCloseIconClickListener {
            toast(getString(R.string.close_is_clicked))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = ChipsFragment()
    }
}
