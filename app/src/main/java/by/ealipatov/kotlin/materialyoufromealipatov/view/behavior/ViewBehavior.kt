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
        return (dependency.id == R.id.tab_layout)
    }


    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        if (dependency.id == R.id.tab_layout) {
            child.y = dependency.y + dependency.height
        }
        return super.onDependentViewChanged(parent, child, dependency)
    }
}