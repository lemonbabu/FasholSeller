package com.fashol.seller.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.fashol.seller.databinding.ActivitySplashScreenBinding
import kotlinx.android.synthetic.main.activity_splash_screen.*

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private var mHandler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        mHandler = Handler(mainLooper)
        doWork()

    }


    private fun doWork() {
        val prg = 3000
        Thread{
            for (i in  0..100) {
                Thread.sleep(30)
                mHandler!!.post { progressBar.progress = i }
            }
        }.start()

        mHandler!!.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, prg.toLong())
    }

}