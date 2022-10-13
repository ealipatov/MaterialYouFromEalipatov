package by.ealipatov.kotlin.materialyoufromealipatov.view.ViewPagerPOD

import android.os.Build
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import by.ealipatov.kotlin.materialyoufromealipatov.view.ViewPager.EarthFragment
import by.ealipatov.kotlin.materialyoufromealipatov.view.ViewPager.MarsFragment
import by.ealipatov.kotlin.materialyoufromealipatov.view.ViewPager.SolarSystemFragment
import java.time.LocalDate


class PODViewPagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    private val fragments = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        arrayOf(
            PictureOfTheDayByDateFragment(LocalDate.now()),
            PictureOfTheDayByDateFragment(LocalDate.now().minusDays(1)),
            PictureOfTheDayByDateFragment(LocalDate.now().minusDays(2))
        )
    } else {
        arrayOf(
            EarthFragment(),
            MarsFragment(),
            SolarSystemFragment()
        )
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }


}
