package by.ealipatov.kotlin.materialyoufromealipatov.view.ViewPagerPOD

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import by.ealipatov.kotlin.materialyoufromealipatov.R
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentPictureOfTheDayViewPagerBinding

import com.google.android.material.tabs.TabLayoutMediator

class PictureOfTheDayViewPagerFragment : Fragment() {

    private var _binding: FragmentPictureOfTheDayViewPagerBinding? = null
    private val binding: FragmentPictureOfTheDayViewPagerBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =FragmentPictureOfTheDayViewPagerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.adapter = PODViewPagerAdapter(this)
        setupTab()
    }

    private fun setupTab() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                TODAY -> {
                    getString(R.string.today)
                }
                YESTERDAY -> {
                    getString(R.string.yesterday)
                }
                DB_YESTERDAY -> {
                    getString(R.string.day_before_yesterday)
                }
                else -> {
                    getString(R.string.earth_tab_text)
                }
            }
        }.attach()

    }

    companion object {
        private const val TODAY = 0
        private const val YESTERDAY = 1
        private const val DB_YESTERDAY = 2
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}