package com.fashol.seller.ui.activity

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.fashol.seller.R
import com.fashol.seller.ui.fragment.DashboardFragment
import com.fashol.seller.utilits.FragmentCommunicator

class MainActivity : AppCompatActivity(), FragmentCommunicator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        menuHome()


    }

    override fun passData(txt: String) {
//        val bundle = Bundle()
//        bundle.putString("message", textView)

        //val transaction = this.supportFragmentManager.beginTransaction()

        when (txt) {
            "deliveryList" -> {

            }
            else -> {
                menuHome()
            }
        }
    }

    private fun menuHome() {
        replaceFragment(DashboardFragment())
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.nav_default_enter_anim, R.anim.nav_default_pop_exit_anim)
        fragmentTransaction.replace(R.id.fcvMain, fragment)
        fragmentTransaction.commit()
    }
}