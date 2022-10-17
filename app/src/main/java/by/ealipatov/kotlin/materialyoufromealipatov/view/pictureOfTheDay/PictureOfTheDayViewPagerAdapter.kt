package by.ealipatov.kotlin.materialyoufromealipatov.view.pictureOfTheDay

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.time.LocalDate


class PictureOfTheDayViewPagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    @RequiresApi(Build.VERSION_CODES.O)
    private val fragments =
        arrayOf(
            PictureOfTheDayByDateFragment(TODAY),
            PictureOfTheDayByDateFragment(YESTERDAY),
            PictureOfTheDayByDateFragment(DB_YESTERDAY)
        )

    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        private val TODAY = LocalDate.now()

        @RequiresApi(Build.VERSION_CODES.O)
        private val YESTERDAY = LocalDate.now().minusDays(1)

        @RequiresApi(Build.VERSION_CODES.O)
        private val DB_YESTERDAY = LocalDate.now().minusDays(2)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getItemCount(): Int {
        return fragments.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }


}
