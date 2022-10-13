package by.ealipatov.kotlin.materialyoufromealipatov.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.ealipatov.kotlin.materialyoufromealipatov.R
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.BottomNavigationLayoutBinding
import by.ealipatov.kotlin.materialyoufromealipatov.utils.toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private var _binding: BottomNavigationLayoutBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomNavigationLayoutBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_one -> {
                    requireActivity().supportFragmentManager.apply {
                        //при нескольких нажатиях на пункт меню, не "плодим" фрагменты
                        if (this.findFragmentByTag("chips") == null) {
                            beginTransaction()
                                .add(R.id.bottom_navigation_container, ChipsFragment.newInstance(), "chips")
                                .hide(this.fragments.last())
                                .hide(this.fragments.first())
                                .addToBackStack(null)
                                .commit()
                        }
                    }
                }
                R.id.navigation_two -> toast(getString(R.string.two_fragment))
            }
            true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
