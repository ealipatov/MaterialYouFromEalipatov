package by.ealipatov.kotlin.materialyoufromealipatov.view.viewPager

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

import by.ealipatov.kotlin.materialyoufromealipatov.R
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentViewPagerBinding

import com.google.android.material.tabs.TabLayoutMediator

class ViewPagerFragment : Fragment() {

    private var _binding: FragmentViewPagerBinding? = null
    private val binding: FragmentViewPagerBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewPagerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.adapter = ViewPagerAdapter(this)
        setupTab()
    }

    /***
     * Функция по настройке TabLayoutMediator (код из текста урока)
     */
    private fun setupTab() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                EARTH -> {
                    getString(R.string.earth_tab_text)
                }
                MARS -> {
                    getString(R.string.mars_tab_text)
                }
                WEATHER -> {
                    getString(R.string.solar_system_tab_text)
                }
                else -> {
                    getString(R.string.earth_tab_text)
                }
            }

            tab.icon = when (position) {
                EARTH -> {
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_earth)
                }
                MARS -> {
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_mars)
                }
                WEATHER -> {
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_system)
                }
                else -> {
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_earth)
                }
            }
        }.attach()

    }



    companion object {
        private const val EARTH = 0
        private const val MARS = 1
        private const val WEATHER = 2
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}