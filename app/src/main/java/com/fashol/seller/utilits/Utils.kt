package com.fashol.seller.utilits

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.view.WindowInsets
import android.view.WindowManager
import com.squareup.picasso.Picasso

object Utils {

    private  var token: String = ""

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

    fun baseUrl(): String{
        return "https://api.fashol.com"
    }

    fun token(): String{
        return token
    }

    @JvmName("setToken1")
    fun setToken(t: String){
        token = t
    }
}