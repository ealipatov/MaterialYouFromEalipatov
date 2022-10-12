package by.ealipatov.kotlin.materialyoufromealipatov.view.ViewPager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentViewPagerBinding

@Suppress("UNREACHABLE_CODE")
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

            binding.viewPager.adapter = ViewPagerAdapter(childFragmentManager)
            binding.tabLayout.setupWithViewPager(binding.viewPager)
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}