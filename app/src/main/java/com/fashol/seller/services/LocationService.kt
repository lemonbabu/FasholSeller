package com.fashol.seller.services

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.fashol.seller.R
import com.fashol.seller.data.api.ApiInterfaces
import com.fashol.seller.data.api.RetrofitClient
import com.fashol.seller.data.repository.local.SellerProfile
import com.fashol.seller.utilits.Utils
import com.fashol.seller.view.ui.activity.MainActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.*
import retrofit2.awaitResponse


@DelicateCoroutinesApi
class LocationService: Service() {

    val CHANNEL_ID = "fasholNotification"
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var lat = ""
    private var lon = ""
    private val locationApi: ApiInterfaces.LocationSendingInterface by lazy { RetrofitClient.sendLocation() }

    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
       // Toast.makeText(this, "service starting $intent", Toast.LENGTH_SHORT).show()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        serviceLoop()

        // Create an explicit intent for an Activity in your app
        val serviceIntent = Intent(this, MainActivity::class.java)

       // Toast.makeText(this, "service starting $intent == $serviceIntent", Toast.LENGTH_SHORT).show()

        val pendingIntent: PendingIntent = if(intent == serviceIntent){
            PendingIntent.getActivity(this, 0, serviceIntent, 0)
        }else{
            PendingIntent.getActivity(this, 0, serviceIntent, 0)
        }



        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo_fashol)
            .setContentTitle("Fashol Seller App")
            .setContentText("Application is running...")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, builder)
        return START_NOT_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show()
    }

    private fun serviceLoop(){
        GlobalScope.launch(Dispatchers.IO) {
            while (true){
                if (ActivityCompat.checkSelfPermission(
                        applicationContext,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        applicationContext,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ){

                    withContext(Dispatchers.Main){
                        // location


                    }
                }

                if(SellerProfile.flag){
                    Log.d("Service", "Service class running...  UserId= ${SellerProfile.data.id} Location= $lat , $lon")
                    getCurrentLocation()

                    if(SellerProfile.flag && (lat != "" && lon != "")){
                        locationApi()
                    }
                }
                delay(30000L)
            }
        }
    }


    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        try {
            fusedLocationClient.lastLocation
                .addOnCompleteListener { location ->
                if (location.isSuccessful) {
                    val currentLocation = location.result
                    if (currentLocation != null) {
                        lat = currentLocation.latitude.toString()
                        lon = currentLocation.longitude.toString()
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("Location= ", e.toString())
        }
    }

    private fun locationApi(){
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = locationApi.sendLocation(SellerProfile.data.id.toString(), lat, lon, "Bearer ${Utils.token()}").awaitResponse()
                Log.d("Location: ",  response.toString())
            }catch (e: Exception) {
                Log.d("Location error= ", e.toString())

            }
        }
    }

}