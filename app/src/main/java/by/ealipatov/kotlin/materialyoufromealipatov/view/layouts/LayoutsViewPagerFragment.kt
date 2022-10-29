package by.ealipatov.kotlin.materialyoufromealipatov.view.layouts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.ealipatov.kotlin.materialyoufromealipatov.R
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentLayoutsViewPagerBinding
import com.google.android.material.tabs.TabLayoutMediator

class LayoutsViewPagerFragment: Fragment() {

    private var _binding: FragmentLayoutsViewPagerBinding? = null
    private val binding: FragmentLayoutsViewPagerBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLayoutsViewPagerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.viewPager.adapter = LayoutsViewPagerAdapter(this)
        setupTab()

    }
    private fun setupTab() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                CONSTRAINT -> {
                    getString(R.string.constraint_layout)
                }
                COORDINATOR -> {
                    getString(R.string.coordinator_layout)
                }
                MOTION -> {
                    getString(R.string.motion_layout)
                }
                else -> {
                    getString(R.string.constraint_layout)
                }
            }
        }.attach()

    }

    companion object {
        private const val CONSTRAINT = 0
        private const val COORDINATOR = 1
        private const val MOTION = 2
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}