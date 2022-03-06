package com.fashol.seller.view.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.fashol.seller.databinding.ActivityPermissionBinding
import com.fashol.seller.utilits.Utils
import kotlinx.coroutines.DelicateCoroutinesApi
import rebus.permissionutils.PermissionUtils


@DelicateCoroutinesApi
class PermissionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPermissionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPermissionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Utils.fullScreen(this)

        binding.button.setOnClickListener {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1000)
        }
    }


    //For User Permissions
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 1000){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Location Permission granted", Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, SplashScreen::class.java))
                finish()
            } else{
                Toast.makeText(this, "Location Permission denied, Without permission we can't use this app", Toast.LENGTH_SHORT).show()
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1000)
                PermissionUtils.openApplicationSettings(
                    this, this.packageName
                )
            }
        }
    }

}