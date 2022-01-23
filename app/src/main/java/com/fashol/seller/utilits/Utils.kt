package com.fashol.seller.utilits

import android.app.Activity
import android.os.Build
import android.view.WindowInsets
import android.view.WindowManager

object Utils {

    fun fullScreen(activity: Activity){
        val window = activity.window

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }
}