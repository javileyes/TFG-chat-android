package es.javiergimenez.chat.service.tool

import android.app.Activity
import android.app.Dialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.ProgressBar
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import es.javiergimenez.chat.R


object ProgressBarTool {

    fun create(activity: Activity, @ColorRes color: Int? = null): Dialog {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.include_progressbar)

        if (color != null) {
            val states: Array<IntArray> = arrayOf(
                intArrayOf(android.R.attr.state_enabled),
                intArrayOf(-android.R.attr.state_enabled),
                intArrayOf(-android.R.attr.state_checked),
                intArrayOf(android.R.attr.state_pressed)
            )

            val colors: IntArray = intArrayOf(
                ContextCompat.getColor(activity, color),
                Color.BLACK,
                Color.BLACK,
                Color.BLACK
            )
            val myList = ColorStateList(states, colors)
            (dialog.findViewById(R.id.progressBar) as ProgressBar).indeterminateTintList = myList
        }

        val windowAttributes = dialog.window!!.attributes
        val display = activity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        windowAttributes.width = size.x
        windowAttributes.height = size.y

        return dialog
    }

}