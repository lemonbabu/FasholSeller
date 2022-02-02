package com.fashol.seller.view.ui.fragment.cart

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.fashol.seller.R
import com.fashol.seller.data.api.ApiInterfaces
import com.fashol.seller.data.api.RetrofitClient
import com.fashol.seller.data.model.productdata.CartItemDataModel
import com.fashol.seller.data.model.productdata.ProductDetailsDataModel
import com.fashol.seller.data.repository.local.CartData
import com.fashol.seller.databinding.FragmentProductDetailsBinding
import com.fashol.seller.utilits.PopUpFragmentCommunicator
import com.fashol.seller.utilits.Utils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_product_details.*
import kotlinx.coroutines.*
import retrofit2.awaitResponse
import kotlin.properties.Delegates


@DelicateCoroutinesApi
class ProductDetailsFragment : Fragment(R.layout.fragment_product_details) {

    private lateinit var binding: FragmentProductDetailsBinding
    private lateinit var fcPopUp: PopUpFragmentCommunicator
    private val productApi: ApiInterfaces.ProductDetailsInterface by lazy { RetrofitClient.getProductDetails() }
    private var productId by Delegates.notNull<Int>()
    private var customerId by Delegates.notNull<Int>()
    private lateinit var listAdapter : ArrayAdapter<String>
    private var variantAdapter : ArrayList<ProductDetailsDataModel.Result.Variant> = arrayListOf()
    private var cartData: ArrayList<CartItemDataModel> = arrayListOf()
    private var count: Int = 0
    private var avatarLink = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductDetailsBinding.bind(view)
        fcPopUp = activity as PopUpFragmentCommunicator

        binding.pbLoading.visibility = View.VISIBLE

        loadData()
//
//        val items = listOf("Material", "Design", "Components", "Android")
//        val item : ArrayList<ProductDetailsDataModel.Result.Variant> = arrayListOf()
//        item.add(ProductDetailsDataModel.Result.Variant(name = "Small", id = 1, price = 334.0))
//        val adapter = ArrayAdapter(requireContext(), R.layout.layout_list_item, item)
//        (lstVariant.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        binding.tvClose.setOnClickListener {
            fcPopUp.passPopUpData("close")
        }

        (binding.lstVariant.editText as AutoCompleteTextView).onItemClickListener =
            OnItemClickListener { _, _, position, _ ->
                val selectedValue: String = listAdapter.getItem(position).toString()
                binding.tvProductPrice.text = variantAdapter[position].price.toString()
            }

        binding.etProductQuantity.addTextChangedListener {
            val qun = it.toString().trim()
            val uPrice = binding.tvProductPrice.text.toString().trim()

            if(binding.etProductQuantity.text.isEmpty()){
                binding.etProductQuantity.error = "Quantity need to fill up!"
                binding.etProductQuantity.requestFocus()
                return@addTextChangedListener
            }

            if(binding.lstVariant.isEmpty()){
                Toast.makeText(context, "Select Variant required", Toast.LENGTH_SHORT).show()
            } else{
                binding.tvSubtotal.text = (qun.toFloat() * uPrice.toFloat()).toString()
            }
        }

        binding.btnAddCart.setOnClickListener {
            val qun = binding.etProductQuantity.text.toString().trim()
            val variant = binding.lstVariant.toString().trim()
            if(qun.isEmpty()){
                binding.etProductQuantity.error = "Quantity need to fill up!"
                binding.etProductQuantity.requestFocus()
                return@setOnClickListener
            }
            if(binding.lstVariant.isEmpty()){
                Toast.makeText(context, "Select Variant required", Toast.LENGTH_SHORT).show()
            }else{
                addItemInCart()
                fcPopUp.passPopUpData("close")
            }

        }


    }

    private fun loadData(){
        val sharedPreferences = activity?.getSharedPreferences("Cart", Context.MODE_PRIVATE)
        val remember = sharedPreferences?.getBoolean("session", false)
        if(remember == true){
            customerId = sharedPreferences.getInt("customerId", 0)
            productId = sharedPreferences.getInt("productId", 0)
            binding.tvProductName.text = sharedPreferences.getString("productName", "....").toString()
            avatarLink = sharedPreferences.getString("productAvatar", "").toString()
            val url = Utils.baseUrl() + avatarLink
            //load image into view
            Picasso.get().load(url).placeholder(R.drawable.placeholder).into(binding.ivProductImage)

            loadProductDetails()
        }
    }

    private fun loadProductDetails(){
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = productApi.getProductDetails(customerId, productId, "Bearer ${Utils.token()}").awaitResponse()
                withContext(Dispatchers.Main){
                    if (response.body()?.success == true){
                        //Toast.makeText(context, response.body()?.message.toString() , Toast.LENGTH_SHORT).show()
                        response.body()?.result?.let {
                            Log.d("Product variant List: ",  it.variants.toString())
                            variantAdapter = it.variants as ArrayList<ProductDetailsDataModel.Result.Variant>
                            val variantList = arrayListOf<String>()
                            for(item in it.variants){
                                variantList.add(item.name)
                            }
                            listAdapter = ArrayAdapter(requireContext(), R.layout.layout_list_item, variantList)
                            (lstVariant.editText as? AutoCompleteTextView)?.setAdapter(listAdapter)
                        }
                    }else{
                        Toast.makeText(context, response.body()?.message.toString() + response.errorBody() , Toast.LENGTH_SHORT).show()
                    }
                    binding.pbLoading.visibility = View.GONE
                }
            }catch (e: Exception) {
                Log.d(" Error ProductDetails ", e.toString())
                withContext(Dispatchers.Main) {
                    Toast.makeText(context,"Error occur Server not response!!", Toast.LENGTH_SHORT).show()
                    binding.pbLoading.visibility = View.GONE
                }
            }
        }
    }

    private fun addItemInCart(){

        CartData.totalItem = CartData.totalItem +  1
        CartData.cartData.add(
            CartItemDataModel(productId.toString(),
            binding.tvProductName.text.toString().trim(),
            avatarLink,
            binding.tvProductPrice.text.toString().trim().toDouble(),
            binding.etProductQuantity.text.toString().trim().toInt(),
            (lstVariant.editText as AutoCompleteTextView).text.toString().trim())
        )
        CartData.totalAmount = CartData.totalAmount + (binding.tvProductPrice.text.toString().trim().toDouble() * binding.etProductQuantity.text.toString().trim().toInt())


//        var item = 0
//        val sharedPreferences = activity?.getSharedPreferences("Cart", Context.MODE_PRIVATE)
//        val remember = sharedPreferences?.getBoolean("session", false)
//        if(remember==true){
//            item = sharedPreferences.getInt("cartItem", 0)
//            item += 1
//        }
//
//        val editor = sharedPreferences?.edit()
//        editor?.apply{
//            putBoolean("session", true)
//            putInt("cartItem", item)
//        }?.apply()
        Toast.makeText(requireContext(), "Add new Product in Cart", Toast.LENGTH_SHORT).show()
    }

}