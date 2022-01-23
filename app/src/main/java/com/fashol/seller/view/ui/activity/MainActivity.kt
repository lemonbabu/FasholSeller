package com.fashol.seller.view.ui.activity


import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.fashol.seller.R
import com.fashol.seller.databinding.ActivityMainBinding
import com.fashol.seller.utilits.FragmentCommunicator
import com.fashol.seller.utilits.Utils.fullScreen
import com.fashol.seller.view.ui.fragment.DashboardFragment
import com.fashol.seller.view.ui.fragment.OrderListFragment
import com.fashol.seller.view.ui.fragment.SelectCustomerFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.nav_header.view.*

class MainActivity : AppCompatActivity(), FragmentCommunicator, NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private var pressBack = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fullScreen(this)

        //Navigation view

        //Navigation view
        val toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)
        menuHome()

        binding.titleBar.btnNewOrder.setOnClickListener {
            selectCustomer()
        }

        binding.titleBar.btnOpenMenu.setOnClickListener {
            if (binding.drawerLayout.isDrawerVisible(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        binding.navView.getHeaderView(0).tvCloseNav.setOnClickListener {
            if (binding.drawerLayout.isDrawerVisible(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }
        }

        binding.titleBar.btnBack.setOnClickListener {
            menuHome()
        }


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
        binding.titleBar.btnOpenMenu.visibility = View.VISIBLE
        binding.titleBar.btnNewOrder.visibility = View.VISIBLE
        binding.titleBar.btnNotification.visibility = View.VISIBLE
        pressBack = false
    }

    private fun orderList() {
        replaceFragment(OrderListFragment())
        binding.titleBar.btnFilter.visibility = View.VISIBLE
        binding.titleBar.btnBack.visibility = View.VISIBLE
    }

    private fun logout() {

    }

    private fun selectCustomer(){
        replaceFragment(SelectCustomerFragment())
        pressBack = true
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.nav_default_enter_anim, R.anim.nav_default_pop_exit_anim)
        fragmentTransaction.replace(R.id.fcvMain, fragment)
        fragmentTransaction.commit()

        pressBack = true
        binding.titleBar.btnFilter.visibility = View.GONE
        binding.titleBar.btnBack.visibility = View.GONE
        binding.titleBar.btnOpenMenu.visibility = View.GONE
        binding.titleBar.btnNewOrder.visibility = View.GONE
        binding.titleBar.btnNotification.visibility = View.GONE

        if (binding.drawerLayout.isDrawerVisible(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    override fun onBackPressed() {
        if(pressBack){
            menuHome()
        }else{
            android.app.AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Are you sure? Do you want to exit this app?")
                .setPositiveButton("Yes") { _, _ -> finish() }
                .setNegativeButton("No", null)
                .show()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_dashboard -> menuHome()
            R.id.nav_order -> orderList()
            R.id.nav_logout -> logout()
        }
        return true
    }
}