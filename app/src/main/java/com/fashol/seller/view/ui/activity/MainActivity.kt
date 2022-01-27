package com.fashol.seller.view.ui.activity


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.fashol.seller.databinding.ActivityMainBinding
import com.fashol.seller.utilits.MainFragmentCommunicator
import com.fashol.seller.utilits.PopUpFragmentCommunicator
import com.fashol.seller.utilits.Utils.fullScreen
import com.fashol.seller.view.ui.fragment.DashboardFragment
import com.fashol.seller.view.ui.fragment.order.OrderListFragment
import com.fashol.seller.view.ui.fragment.customer.SelectCustomerFragment
import com.fashol.seller.view.ui.fragment.cart.AddProductFragment
import com.fashol.seller.view.ui.fragment.cart.CartFragment
import com.fashol.seller.view.ui.fragment.cart.ProductDetailsFragment
import com.fashol.seller.view.ui.fragment.user.UserProfileFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.nav_header.view.*
import com.fashol.seller.R
import com.fashol.seller.view.ui.fragment.customer.AddNewCustomerFragment
import com.fashol.seller.view.ui.fragment.order.OrderConfirmationFragment


class MainActivity : AppCompatActivity(), MainFragmentCommunicator, PopUpFragmentCommunicator, NavigationView.OnNavigationItemSelectedListener {

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

        binding.navView.getHeaderView(0).tvUserName.setOnClickListener {
            userProfile()
        }

        binding.navView.getHeaderView(0).ivUserAvatar.setOnClickListener {
            userProfile()
        }

        binding.titleBar.btnBack.setOnClickListener {
            menuHome()
        }

        binding.titleBar.btnCart.setOnClickListener {
            if(binding.fcPopUp.isVisible){
                binding.fcPopUp.visibility = View.GONE
                binding.cartFooter.btnCartOpenClose.setImageResource(R.drawable.ic_feather_chevron_up)
            }else{
                cartShow()
            }
        }

        binding.cartFooter.btnCartOpenClose.setOnClickListener {
            if(binding.fcPopUp.isVisible){
                binding.fcPopUp.visibility = View.GONE
                binding.cartFooter.btnCartOpenClose.setImageResource(R.drawable.ic_feather_chevron_up)
            }else{
                cartShow()
            }
        }

        binding.cartFooter.btnOrderSubmit.setOnClickListener {
            orderConfirmationPage()
        }


    }

    override fun passData(txt: String) {
//        val bundle = Bundle()
//        bundle.putString("message", textView)

        //val transaction = this.supportFragmentManager.beginTransaction()

        when (txt) {
            "deliveryList" -> {
            }
            "CustomerProfile" -> userProfile()
            "Dashboard" -> menuHome()
            "OrderList" -> orderList()
            "ProductPage" -> productPage()
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
        binding.titleBar.txtTitle.visibility = View.VISIBLE
        binding.titleBar.txtTitle.text = resources.getString(R.string.app_name)
        pressBack = false
    }

    private fun orderList() {
        replaceFragment(OrderListFragment())
        binding.titleBar.btnFilter.visibility = View.VISIBLE
        binding.titleBar.btnBack.visibility = View.VISIBLE
        binding.titleBar.txtTitle.visibility = View.VISIBLE
        binding.titleBar.txtTitle.text = resources.getString(R.string.order_list)
    }

    private fun userProfile() {
        replaceFragment(UserProfileFragment())
        binding.titleBar.btnBack.visibility = View.VISIBLE
        binding.titleBar.txtTitle.visibility = View.VISIBLE
        binding.titleBar.txtTitle.text = resources.getString(R.string.user_profile)
    }

    private fun addNewCustomer(){
        replaceFragment(AddNewCustomerFragment())
        binding.titleBar.btnBack.visibility = View.VISIBLE
        binding.titleBar.txtTitle.visibility = View.VISIBLE
        binding.titleBar.txtTitle.text = resources.getString(R.string.add_new_customer)
    }

    private fun productPage(){
        replaceFragment(AddProductFragment())
        binding.titleBar.btnBack.visibility = View.VISIBLE
        binding.titleBar.tvCustomerName.visibility = View.VISIBLE
        binding.titleBar.ivCustomerAvatar.visibility = View.VISIBLE
        binding.titleBar.btnCart.visibility = View.VISIBLE
        binding.titleBar.tvCustomerDetails.visibility = View.VISIBLE
        binding.titleBar.tvChartNumberOfItem.visibility = View.VISIBLE
        binding.cartFooter.layoutCartFooter.visibility = View.VISIBLE
        loadProductData()
    }

    private fun orderConfirmationPage(){
        replaceFragment(OrderConfirmationFragment())
        binding.titleBar.btnOpenMenu.visibility = View.VISIBLE
        binding.titleBar.tvCustomerName.visibility = View.VISIBLE
        binding.titleBar.ivCustomerAvatar.visibility = View.VISIBLE
        binding.titleBar.tvCustomerDetails.visibility = View.VISIBLE
    }

    private fun logout() {

    }

    private fun selectCustomer(){
        replaceFragment(SelectCustomerFragment())
        pressBack = true
        binding.titleBar.btnBack.visibility = View.VISIBLE
        binding.titleBar.txtTitle.visibility = View.VISIBLE
        binding.titleBar.txtTitle.text = resources.getString(R.string.select_customer)
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.nav_default_enter_anim, R.anim.nav_default_pop_exit_anim)
        fragmentTransaction.replace(R.id.fcvMain, fragment)
        fragmentTransaction.commit()

        pressBack = true
        binding.cartFooter.layoutCartFooter.visibility = View.GONE
        binding.titleBar.btnFilter.visibility = View.GONE
        binding.titleBar.btnBack.visibility = View.GONE
        binding.titleBar.btnOpenMenu.visibility = View.GONE
        binding.titleBar.btnNewOrder.visibility = View.GONE
        binding.titleBar.btnNotification.visibility = View.GONE
        binding.titleBar.txtTitle.visibility = View.GONE
        binding.titleBar.tvCustomerName.visibility = View.GONE
        binding.titleBar.ivCustomerAvatar.visibility = View.GONE
        binding.titleBar.btnCart.visibility = View.GONE
        binding.titleBar.tvCustomerDetails.visibility = View.GONE
        binding.titleBar.tvChartNumberOfItem.visibility = View.GONE
        binding.fcPopUp.visibility = View.GONE

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
            R.id.nav_user_profile -> userProfile()
            R.id.nav_customer -> addNewCustomer()
        }
        return true
    }

    override fun passPopUpData(txt: String) {
        when (txt) {
            "productDetails" -> productDetails()
            "close" -> {
                binding.fcPopUp.visibility = View.GONE
                loadProductData()
            }
            "CartShow" ->{
                cartShow()
            }
            "OrderList" -> orderList()
            else -> {
                menuHome()
            }

        }
    }

    private fun productDetails(){
        binding.fcPopUp.visibility = View.VISIBLE
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.nav_default_enter_anim, R.anim.nav_default_pop_exit_anim)
        fragmentTransaction.replace(R.id.fcPopUp, ProductDetailsFragment())
        fragmentTransaction.commit()

//        val sharedPreferences: SharedPreferences = getSharedPreferences("Cart", Context.MODE_PRIVATE)
//        val remember = sharedPreferences.getBoolean("session", false)
//        if(remember){
//            binding.titleBar.tvCustomerName.text = sharedPreferences.getString("customerName", "")
//        }

    }

    private fun loadProductData(){
        val sharedPreferences: SharedPreferences = getSharedPreferences("Cart", Context.MODE_PRIVATE)
        val remember = sharedPreferences.getBoolean("session", false)
        if(remember){
            binding.titleBar.tvCustomerName.text = sharedPreferences.getString("customerName", "")
            binding.titleBar.tvChartNumberOfItem.text = sharedPreferences.getInt("cartItem", 0).toString()
        }
    }

    private fun cartShow(){
        binding.fcPopUp.visibility = View.VISIBLE
        binding.cartFooter.btnCartOpenClose.setImageResource(R.drawable.ic_feather_chevron_down)
//        val unwrappedDrawable = AppCompatResources.getDrawable(applicationContext, R.drawable.ic_feather_chevron_up)
//        val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
//        DrawableCompat.setTint(wrappedDrawable, Color.RED)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.nav_default_enter_anim, com.fashol.seller.R.anim.nav_default_pop_exit_anim)
        fragmentTransaction.replace(R.id.fcPopUp, CartFragment())
        fragmentTransaction.commit()
    }

}