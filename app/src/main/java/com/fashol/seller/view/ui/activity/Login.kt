package com.fashol.seller.view.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.fashol.seller.databinding.ActivityLoginBinding
import com.fashol.seller.utilits.Utils
import kotlinx.coroutines.DelicateCoroutinesApi


@DelicateCoroutinesApi
class Login : AppCompatActivity(){

    private lateinit var binding: ActivityLoginBinding
    private val url = "https://api.fashol.com/v1/sales-executive/login"

    @SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.wvLogin.settings.javaScriptEnabled = true
        binding.wvLogin.settings.javaScriptCanOpenWindowsAutomatically = true
        binding.wvLogin.webViewClient = MyWebVIew()

        binding.btnLogin.setOnClickListener {
            login()
        }


    }

    private fun login(){
        binding.wvLogin.visibility = View.VISIBLE
        binding.btnLogin.visibility = View.GONE
        binding.wvLogin.loadUrl(url)
    }

    inner class MyWebVIew: WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            if (url != null) {
                if (url.contains("https://api.fashol.com/access-token")) {
                    val token = url.split("https://api.fashol.com/access-token/")
                    gotoHome(token[1])
                    return true
                } else{
                    binding.wvLogin.loadUrl(url)
                }
            }
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
//        Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
//            startActivity(this)
//        }
            return false
        }

        override fun onPageFinished(view: WebView?, url: String?) {

        }
    }

    private fun gotoHome(token: String){
        Log.d("Access Token: ", token)
        val sharedPreferences = getSharedPreferences("Session", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply{
            putBoolean("session", true)
            putString("token", token)
        }.apply()

        Utils.setToken(token)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }



}

