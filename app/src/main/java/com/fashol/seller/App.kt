package com.fashol.seller

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class App: Application() {

    val CHANNEL_ID = "fasholNotification"

    override fun onCreate() {
        super.onCreate()
        createNot()
    }

    private fun createNot(){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
            val serviceChannel: NotificationChannel = NotificationChannel(
                CHANNEL_ID,
                "Fashol Seller App",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val manager: NotificationManager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

}