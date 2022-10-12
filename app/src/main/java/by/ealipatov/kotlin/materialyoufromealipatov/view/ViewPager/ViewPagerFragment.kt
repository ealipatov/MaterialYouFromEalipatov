package by.ealipatov.kotlin.materialyoufromealipatov.view.ViewPager

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import by.ealipatov.kotlin.materialyoufromealipatov.MainActivity
import by.ealipatov.kotlin.materialyoufromealipatov.R
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentViewPagerBinding
import by.ealipatov.kotlin.materialyoufromealipatov.view.BottomNavigationDrawerFragment
import by.ealipatov.kotlin.materialyoufromealipatov.view.SettingFragment
import com.google.android.material.bottomappbar.BottomAppBar
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
        setBottomAppBar(view)
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

    //меню
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar_view_pager_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.app_bar_back -> requireActivity().supportFragmentManager.apply {
                popBackStack()
            }

            R.id.app_bar_settings -> requireActivity().supportFragmentManager.apply {
                beginTransaction()
                    .add(R.id.container, SettingFragment())
                    .hide(this.fragments.last())
                    .addToBackStack(tag)
                    .commit()
            }

            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "tag")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBottomAppBar(view: View) {
        val context = activity as MainActivity
        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        setHasOptionsMenu(true)
        binding.fab.setOnClickListener {
            if (isMain) {
                isMain = false
                binding.bottomAppBar.navigationIcon = null
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_back_fab
                    )
                )
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
            } else {
                isMain = true
                binding.bottomAppBar.navigationIcon =
                    ContextCompat.getDrawable(context, R.drawable.ic_hamburger_menu_bottom_bar)
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_plus_fab
                    )
                )
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
            }
        }
    }

    companion object {
        private const val EARTH = 0
        private const val MARS = 1
        private const val WEATHER = 2

        internal var isMain = true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}