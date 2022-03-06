package com.fashol.seller.view.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.fashol.seller.BuildConfig
import com.fashol.seller.data.api.ApiInterfaces
import com.fashol.seller.data.api.RetrofitClient
import com.fashol.seller.databinding.ActivitySplashScreenBinding
import com.fashol.seller.utilits.Utils
import com.fashol.seller.utilits.Utils.fullScreen
import kotlinx.android.synthetic.main.activity_splash_screen.*
import kotlinx.coroutines.*
import retrofit2.awaitResponse

@DelicateCoroutinesApi
@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private var mHandler: Handler? = null
    private var versionName: String = BuildConfig.VERSION_NAME
    private val versionApi: ApiInterfaces.VersionCheckingInterface by lazy { RetrofitClient.getVersion() }

    private var code = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fullScreen(this)

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), code)
        } else {
            loadVersion()

            mHandler = Handler(mainLooper)
            doWork()
        }
    }


    private fun doWork() {
        val prg = 3000
        Thread{
            for (i in  0..100) {
                Thread.sleep(30)
                mHandler!!.post { progressBar.progress = i }
            }
        }.start()
    }

    private fun session(): Boolean{
        val sharedPreferences: SharedPreferences = getSharedPreferences("Session", Context.MODE_PRIVATE)
        val remember = sharedPreferences.getBoolean("session", false)
        if(remember){
            val token = sharedPreferences.getString("token", "")
            token?.let { Utils.setToken(it) }
            return true
        }
        return false
    }

    // load category data from api using coroutine
    private fun loadVersion(){
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = versionApi.getVersion().awaitResponse()
                withContext(Dispatchers.Main){
                    Log.d("Category List: ",  response.toString())
                    if (response.body()?.result?.version != versionName) {
                        AlertDialog.Builder(this@SplashScreen)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Update Required")
                            .setMessage("Update your apps form play store")
                            .setPositiveButton("Update") { _, _ ->
                                val uri = Uri.parse("https://play.google.com/store/apps/details?id=com.fashol.seller")
                                //val uri = Uri.parse("https://play.google.com/store/apps/details?id=null")
                                startActivity(Intent(Intent.ACTION_VIEW, uri))
                                finish()
                            }
                            .setNegativeButton("Close", null)
                            .show()
                    }else{
                        delay(2000L)
                        if(session()){
                            startActivity(Intent(applicationContext, MainActivity::class.java))
                            finish()
                        }else{
                            startActivity(Intent(applicationContext, Login::class.java))
                            finish()
                        }
                    }
                }
            }catch (e: Exception) {
                Log.d(" Error Category ", e.toString())
                withContext(Dispatchers.Main) {
                    //Toast.makeText(applicationContext,"Internet not stable or Server error occur!!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //For User Permissions
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == code){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Location Permission granted", Toast.LENGTH_SHORT).show()

                loadVersion()

                mHandler = Handler(mainLooper)
                doWork()
            } else{
                Toast.makeText(this, "Location Permission denied, Without permission we can't use this app", Toast.LENGTH_SHORT).show()
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), code)
                startActivity(Intent(applicationContext, PermissionActivity::class.java))
                finish()
            }
        }
    }


}