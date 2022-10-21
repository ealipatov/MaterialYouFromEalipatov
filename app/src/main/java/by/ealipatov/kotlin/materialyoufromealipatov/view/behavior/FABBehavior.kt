package by.ealipatov.kotlin.materialyoufromealipatov.view.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import androidx.coordinatorlayout.widget.CoordinatorLayout
import by.ealipatov.kotlin.materialyoufromealipatov.R
import com.google.android.material.appbar.AppBarLayout
import java.lang.StrictMath.abs

class FABBehavior(context: Context, attrs: AttributeSet?=null):
    CoordinatorLayout.Behavior<Button> (context, attrs) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: Button,
        dependency: View
    ): Boolean {
        return (dependency is AppBarLayout || dependency.id == R.id.bottom_sheet_container)
    }


    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: Button,
        dependency: View
    ): Boolean {
        if (dependency is AppBarLayout) {
            child.x = (dependency.width - child.width).toFloat()
            child.y = dependency.y + dependency.height - child.height/2

            child.alpha = 1 - (abs(dependency.y)/(dependency.height/2))
        } else if (dependency.id == R.id.bottom_sheet_container) {
            child.x = (dependency.width - child.width).toFloat()
            child.y = dependency.y - child.height/2
            child.alpha = (abs(dependency.y))
        }
        return super.onDependentViewChanged(parent, child, dependency)
    }
}