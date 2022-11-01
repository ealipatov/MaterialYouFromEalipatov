package by.ealipatov.kotlin.materialyoufromealipatov.view.layouts

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import by.ealipatov.kotlin.materialyoufromealipatov.view.recycler.RecyclerFragment


class LayoutsViewPagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    private val fragments = arrayOf(ConstraintLayoutFragment(), CoordinatorLayoutFragment(), MotionLayoutFragment(), RecyclerFragment())
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }


}
