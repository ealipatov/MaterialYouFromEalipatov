package by.ealipatov.kotlin.materialyoufromealipatov.view.ViewPager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    private val fragments = arrayOf(EarthFragment(), MarsFragment(), SolarSystemFragment())

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getPageTitle(position: Int): String {
        return fragments[position].toString()
    }

}
