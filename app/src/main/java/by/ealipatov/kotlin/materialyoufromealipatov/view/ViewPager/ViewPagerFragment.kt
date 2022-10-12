package by.ealipatov.kotlin.materialyoufromealipatov.view.ViewPager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentViewPagerBinding
import com.google.android.material.tabs.TabLayout
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

            TabLayoutMediator(
                binding.tabLayout,
                binding.viewPager,
                object : TabLayoutMediator.TabConfigurationStrategy {
                    override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                        tab.text = when (position) {
                            0 -> {
                                "Earth"
                            }
                            1 -> {
                                "Mars"
                            }
                            2 -> {
                                "Solar system"
                            }
                            else -> {
                                "Earth"
                            }
                        }
                    }
                }).attach()
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}