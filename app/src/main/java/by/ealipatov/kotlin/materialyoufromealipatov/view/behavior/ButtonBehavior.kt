package by.ealipatov.kotlin.materialyoufromealipatov.view.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import androidx.coordinatorlayout.widget.CoordinatorLayout
import by.ealipatov.kotlin.materialyoufromealipatov.R

class ButtonBehavior(context: Context, attrs: AttributeSet?=null):
    CoordinatorLayout.Behavior<Button> (context, attrs) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: Button,
        dependency: View
    ): Boolean {
        return (dependency.id == R.id.btn_up)
    }


    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: Button,
        dependency: View
    ): Boolean {
        if (dependency.id == R.id.btn_up) {
            child.x = dependency.x + child.width
            child.y = dependency.y + child.height
        }
        return super.onDependentViewChanged(parent, child, dependency)
    }
}