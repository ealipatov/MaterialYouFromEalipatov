package by.ealipatov.kotlin.materialyoufromealipatov.view.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import by.ealipatov.kotlin.materialyoufromealipatov.R

class ViewBehavior(context: Context, attrs: AttributeSet?=null):
    CoordinatorLayout.Behavior<View> (context, attrs) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return (dependency.id == R.id.bottom_sheet_container)
    }


    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        if (dependency.id == R.id.bottom_sheet_container) {
            child.y = dependency.y - 350
        }
        return super.onDependentViewChanged(parent, child, dependency)
    }
}