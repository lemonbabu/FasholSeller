package com.fashol.seller.view.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fashol.seller.data.api.ApiInterfaces
import com.fashol.seller.data.api.RetrofitClient
import com.fashol.seller.data.repository.local.CategoryProductListData
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
    private val categoryApi: ApiInterfaces.CategoryListInterface by lazy { RetrofitClient.getCategoryList() }
    private val productApi: ApiInterfaces.ProductListInterface by lazy { RetrofitClient.getProductList() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fullScreen(this)

        loadCategory()
        loadProduct()

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
            if(session()){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else{
                startActivity(Intent(this, Login::class.java))
                finish()
            }

        }, prg.toLong())
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
    private fun loadCategory(){
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = categoryApi.getCategoryList("Bearer ${Utils.token()}").awaitResponse()
                withContext(Dispatchers.Main){
                    Log.d("Category List: ",  response.toString())
                    if (response.body()?.success == true){
                        //Toast.makeText(context, response.body()?.message.toString() , Toast.LENGTH_SHORT).show()
                        response.body()?.result?.let {
                            CategoryProductListData.flagCat = true
                            CategoryProductListData.dataCat = it
                        }
                    }else{
                        Toast.makeText(applicationContext, response.body()?.message.toString() + response.errorBody() , Toast.LENGTH_SHORT).show()
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

    // load all product form this api
    private fun loadProduct(){
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = productApi.getProductList("Bearer ${Utils.token()}").awaitResponse()
                withContext(Dispatchers.Main){
                    Log.d("Product List: ",  response.toString())
                    if (response.body()?.success == true){
                        //Toast.makeText(context, response.body()?.message.toString() , Toast.LENGTH_SHORT).show()
                        response.body()?.result?.let {
                            CategoryProductListData.flagPro = true
                            CategoryProductListData.dataPro = it
                        }
                    }else{
                        Toast.makeText(applicationContext, response.body()?.message.toString() + response.errorBody() , Toast.LENGTH_SHORT).show()
                    }
                }
            }catch (e: Exception) {
                Log.d(" Error Product ", e.toString())
                withContext(Dispatchers.Main) {
                    //Toast.makeText(applicationContext,"Internet not stable or Server error occur!!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}