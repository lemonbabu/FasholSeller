package com.fashol.seller.view.ui.activity
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.fashol.seller.R
import com.fashol.seller.data.api.ApiInterfaces
import com.fashol.seller.data.api.RetrofitClient
import com.fashol.seller.data.repository.local.CartData
import com.fashol.seller.data.repository.local.OrderListData
import com.fashol.seller.data.repository.local.SellerProfile
import com.fashol.seller.databinding.ActivityMainBinding
import com.fashol.seller.utilits.MainFragmentCommunicator
import com.fashol.seller.utilits.PopUpFragmentCommunicator
import com.fashol.seller.utilits.Utils
import com.fashol.seller.utilits.Utils.fullScreen
import com.fashol.seller.view.ui.fragment.DashboardFragment
import com.fashol.seller.view.ui.fragment.cart.AddProductFragment
import com.fashol.seller.view.ui.fragment.cart.CartFragment
import com.fashol.seller.view.ui.fragment.cart.ProductDetailsFragment
import com.fashol.seller.view.ui.fragment.customer.AddNewCustomerFragment
import com.fashol.seller.view.ui.fragment.customer.CustomerListFragment
import com.fashol.seller.view.ui.fragment.customer.SelectCustomerFragment
import com.fashol.seller.view.ui.fragment.notice.NoticeListFragment
import com.fashol.seller.view.ui.fragment.order.OrderConfirmationFragment
import com.fashol.seller.view.ui.fragment.order.OrderDetailsFragment
import com.fashol.seller.view.ui.fragment.order.OrderListFragment
import com.fashol.seller.view.ui.fragment.profile.UserProfileFragment
import com.google.android.material.navigation.NavigationView
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.nav_header.view.*
import kotlinx.coroutines.*
import retrofit2.awaitResponse


@DelicateCoroutinesApi
class MainActivity : AppCompatActivity(), MainFragmentCommunicator, PopUpFragmentCommunicator, NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHeader: View
    private var pressBack = false
    private var customerFlag = true
    private val orderApi: ApiInterfaces.CreateOrderInterface by lazy { RetrofitClient.newOrder() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fullScreen(this)

        //Navigation view
        val toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)
        navHeader = binding.navView.getHeaderView(0)

        menuHome()

        binding.titleBar.btnNewOrder.setOnClickListener {
            customerFlag = if(customerFlag){
                selectCustomer()
                false
            } else{
                addNewCustomer()
                true
            }

        }

        binding.titleBar.btnOpenMenu.setOnClickListener {
            if (binding.drawerLayout.isDrawerVisible(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        navHeader.tvCloseNav.setOnClickListener {
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
            if(CartData.totalItem == 0)
            {
                Toast.makeText(applicationContext, "Please add Product first?!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else{
                binding.pbLoading.visibility = View.VISIBLE

                val list  = JsonArray()

                for (item in CartData.cartData){
                    val p = JsonObject()
                    p.addProperty("product_id", item.id.toInt())
                    p.addProperty("variant_id", item.variantId)
                    p.addProperty("quantity", item.quantity.toDouble())
                    p.addProperty("note", "")
                    p.addProperty("status", "add")
                    list.add(p)
                }

                val body = JsonObject()
                body.addProperty("assigned_by",2)
                body.addProperty("customer_id",CartData.customerId)
                body.addProperty("note",CartData.orderNote)
                body.add("order_lists", list)

                Log.d("Body ", body.toString())
                orderApi(body)
                //orderConfirmationPage()
            }

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
            "CustomerList" -> customerList()
            "Dashboard" -> menuHome()
            "OrderList" -> orderList()
            "OrderDetails" -> orderDetails()
            "ProductPage" -> productPage()
            "NoticeList" -> noticeListPage()
            "loadProfile" -> loadUserData()
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

    private fun orderDetails(){
        replaceFragment(OrderDetailsFragment())
        binding.titleBar.btnFilter.visibility = View.VISIBLE
        binding.titleBar.btnBack.visibility = View.VISIBLE
        binding.titleBar.txtTitle.visibility = View.VISIBLE
        binding.titleBar.txtTitle.text = resources.getString(R.string.order_detail)
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

    private fun noticeListPage(){
        replaceFragment(NoticeListFragment())
        binding.titleBar.btnBack.visibility = View.VISIBLE
        binding.titleBar.txtTitle.visibility = View.VISIBLE
        binding.titleBar.txtTitle.text = resources.getString(R.string.notice_board)
    }

    private fun customerList(){
        replaceFragment(CustomerListFragment())
        binding.titleBar.btnBack.visibility = View.VISIBLE
        binding.titleBar.txtTitle.visibility = View.VISIBLE
        binding.titleBar.btnNewOrder.visibility = View.VISIBLE
        binding.titleBar.txtTitle.text = resources.getString(R.string.customer_list)
        customerFlag = false
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
        binding.titleBar.btnNewOrder.visibility = View.VISIBLE
        binding.titleBar.txtTitle.text = resources.getString(R.string.select_customer)
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.nav_default_enter_anim, R.anim.nav_default_pop_exit_anim)
        fragmentTransaction.replace(R.id.fcvMain, fragment)
        fragmentTransaction.commit()

        pressBack = true
        customerFlag = true
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
        if(pressBack && !binding.fcPopUp.isVisible){
            menuHome()
        }else if(pressBack && binding.fcPopUp.isVisible){
            binding.fcPopUp.visibility = View.GONE
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
            R.id.nav_customer -> customerList()
            R.id.nav_notice -> noticeListPage()
        }
        return true
    }

    override fun passPopUpData(txt: String) {
        when (txt) {
            "productDetails" -> productDetails()
            "close" -> {
                binding.fcPopUp.visibility = View.GONE
                binding.cartFooter.layoutCartFooter.visibility = View.VISIBLE
                binding.cartFooter.layoutCartFooter.visibility = View.VISIBLE
                loadProductData()
            }
            "CartShow" -> cartShow()
            "OrderList" -> orderList()
            "reloadCart" -> reloadCart()
            else -> {
                menuHome()
            }

        }
    }

    private fun productDetails(){
        binding.fcPopUp.visibility = View.VISIBLE
        binding.fcvMain.isEnabled = false
        binding.cartFooter.layoutCartFooter.visibility = View.GONE
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

    @SuppressLint("SetTextI18n")
    private fun loadProductData(){
        binding.titleBar.tvCustomerName.text = CartData.customerName
        binding.titleBar.tvChartNumberOfItem.text = CartData.totalItem.toString()
        binding.cartFooter.tvNumberOfItem.text = CartData.totalItem.toString()
        binding.cartFooter.tvTotalPrice.text = CartData.totalAmount.toString() + " " + getString(R.string.taka)
        val sharedPreferences: SharedPreferences = getSharedPreferences("Cart", Context.MODE_PRIVATE)
        val remember = sharedPreferences.getBoolean("session", false)
        if(remember){
            binding.titleBar.tvCustomerName.text = sharedPreferences.getString("customerName", "")
            val url = Utils.baseUrl() +  sharedPreferences.getString("customerAvatar", " ").toString()

            // load image into view
            Picasso.get().load(url).placeholder(R.drawable.placeholder).into(binding.titleBar.ivCustomerAvatar)
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
        fragmentTransaction.setCustomAnimations(R.anim.nav_default_enter_anim, R.anim.nav_default_pop_exit_anim)
        fragmentTransaction.replace(R.id.fcPopUp, CartFragment())
        fragmentTransaction.commit()
    }

    private fun reloadCart(){
        loadProductData()
    }

    // API calling for Order Confirmations
    private fun orderApi(body: JsonObject){
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = orderApi.setNewOrder(body, "Bearer ${Utils.token()}").awaitResponse()
                withContext(Dispatchers.Main){
                    if (response.body()?.success == true){
                        //Toast.makeText(context, response.body()?.message.toString() , Toast.LENGTH_SHORT).show()
                        response.body()?.result?.let {
                            Log.d("Order Confirmed: ",  it.toString())
                            OrderListData.flag = false // this is for call order list api again
                            SellerProfile.flag = false
                            orderConfirmationPage()
                        }
                    }else{
                        Toast.makeText(applicationContext, response.body()?.message.toString() + response.errorBody() , Toast.LENGTH_SHORT).show()
                    }
                    binding.pbLoading.visibility = View.GONE
                }
            }catch (e: Exception) {
                Log.d(" Error to Order ", e.toString())
                withContext(Dispatchers.Main) {
                    Toast.makeText(applicationContext,"Error occur Server not response!!", Toast.LENGTH_SHORT).show()
                    binding.pbLoading.visibility = View.GONE
                }
            }
        }
    }

    // Seller Profile and dashboard data and data load from the object
    private fun loadUserData(){
        navHeader.tvUserName.text = SellerProfile.data.name
        navHeader.tvUserPhone.text = SellerProfile.data.phone
        val url = Utils.baseUrl() +  SellerProfile.data.avatar

        // load image into view
        Picasso.get().load(url).placeholder(R.drawable.placeholder).into(navHeader.ivUserAvatar)
    }

}

