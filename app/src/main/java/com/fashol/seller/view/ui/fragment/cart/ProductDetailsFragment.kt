package com.fashol.seller.view.ui.fragment.cart

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.fashol.seller.R
import com.fashol.seller.data.api.ApiInterfaces
import com.fashol.seller.data.api.RetrofitClient
import com.fashol.seller.data.model.orderdata.CartItemDataModel
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
    private var vList: ArrayList<ProductDetailsDataModel.Result.Variant> = arrayListOf()
    private var avatarLink = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductDetailsBinding.bind(view)
        fcPopUp = activity as PopUpFragmentCommunicator

        binding.pbLoading.visibility = View.VISIBLE

        loadData()

        binding.tvClose.setOnClickListener {
            fcPopUp.passPopUpData("close")
        }

        (binding.lstVariant.editText as AutoCompleteTextView).onItemClickListener =
            OnItemClickListener { _, _, position, _ ->
                val selectedValue: String = listAdapter.getItem(position).toString()
                binding.tvProductPrice.text = variantAdapter[position].price.toString()
            }

        binding.etProductQuantity.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                if((lstVariant.editText as AutoCompleteTextView).text.toString().trim().isEmpty()){
                    Toast.makeText(context, "Select Variant required", Toast.LENGTH_SHORT).show()
                    return
                }
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                binding.tvSubtotal.text = (s.toString().toFloat() * binding.tvProductPrice.text.toString().trim().toFloat()).toString()
            }
        })

        binding.btnAddCart.setOnClickListener {
            val qun = binding.etProductQuantity.text.toString().trim()
            val variant = binding.lstVariant.toString().trim()
            if(qun.isEmpty()){
                binding.etProductQuantity.error = "Quantity need to fill up!"
                binding.etProductQuantity.requestFocus()
                return@setOnClickListener
            }
            if((lstVariant.editText as AutoCompleteTextView).text.toString().trim().isEmpty()){
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
                    Log.d("Response= ", response.toString())
                    if (response.body()?.success == true){
                        //Toast.makeText(context, response.body()?.message.toString() , Toast.LENGTH_SHORT).show()
                        response.body()?.result?.let {
                            Log.d("Product variant List: ",  it.variants.toString())
                            variantAdapter = it.variants as ArrayList<ProductDetailsDataModel.Result.Variant>
                            vList = it.variants as ArrayList<ProductDetailsDataModel.Result.Variant>
                            val variantList = arrayListOf<String>()
                            val variantList2 = arrayListOf<String>()
                            for(item in it.variants){
                                variantList.add(item.name)
                                variantList2.add(item.name + "[" + item.price + "]")
                            }
                            listAdapter = ArrayAdapter(requireContext(), R.layout.layout_list_item, variantList2)
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
        var variantId = 0
        val vItem =((lstVariant.editText as AutoCompleteTextView).text.toString().trim()).split('[')
        for (item in vList){
            if(vItem[0] == item.name){
                variantId = item.id
            }
        }
        CartData.totalItem = CartData.totalItem +  1
        CartData.cartData.add(
            CartItemDataModel(productId.toString(),
            binding.tvProductName.text.toString().trim(),
            avatarLink,
            binding.tvProductPrice.text.toString().trim().toDouble(),
            binding.etProductQuantity.text.toString().trim().toDouble(),
            (lstVariant.editText as AutoCompleteTextView).text.toString().trim(),
            variantId)
        )
        CartData.totalAmount = CartData.totalAmount + (binding.tvProductPrice.text.toString().trim().toDouble() * binding.etProductQuantity.text.toString().trim().toDouble())


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