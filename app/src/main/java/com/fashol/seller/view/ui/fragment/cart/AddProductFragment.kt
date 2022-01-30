package com.fashol.seller.view.ui.fragment.cart

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fashol.seller.R
import com.fashol.seller.data.api.ApiInterfaces
import com.fashol.seller.data.api.RetrofitClient
import com.fashol.seller.data.model.productdata.CategoryDataModel
import com.fashol.seller.data.model.productdata.ProductDataModel
import com.fashol.seller.databinding.FragmentAddProductBinding
import com.fashol.seller.utilits.PopUpFragmentCommunicator
import com.fashol.seller.utilits.Utils
import com.fashol.seller.view.adapter.CategoryAdapter
import com.fashol.seller.view.adapter.ProductAdapter
import kotlinx.coroutines.*
import retrofit2.awaitResponse

@DelicateCoroutinesApi
class AddProductFragment : Fragment(R.layout.fragment_add_product), CategoryAdapter.OnCustomerClickListener, ProductAdapter.OnProductClickListener {

    private lateinit var binding: FragmentAddProductBinding
    private lateinit var fcpopup: PopUpFragmentCommunicator
    private lateinit var productAdapter: ProductAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private val categoryApi: ApiInterfaces.CategoryListInterface by lazy { RetrofitClient.getCategoryList() }
    private val productApi: ApiInterfaces.ProductListInterface by lazy { RetrofitClient.getProductList() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddProductBinding.bind(view)
        fcpopup = activity as PopUpFragmentCommunicator

        binding.pbLoading.visibility = View.VISIBLE
        binding.rvProducts.layoutManager = GridLayoutManager(context, 3)
        productAdapter = ProductAdapter(this)

        binding.rvCategory.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        categoryAdapter = CategoryAdapter(this)

        loadCategory()
        loadProduct()

    }

    override fun onCustomerClickListener(id: Int, name: String, avatar: String) {
        //
    }

    override fun onProductClickListener(id: Int, name: String, avatar: String) {
        val sharedPreferences = activity?.getSharedPreferences("Cart", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.apply{
            putBoolean("session", true)
            putString("productName", name)
            putString("productAvatar", avatar)
        }?.apply()

        fcpopup.passPopUpData("productDetails")
    }

    private fun loadCategory(){
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = categoryApi.getCategoryList("Bearer ${Utils.token()}").awaitResponse()
                withContext(Dispatchers.Main){
                    Log.d("Category List: ",  response.toString())
                    if (response.body()?.success == true){
                        Toast.makeText(context, response.body()?.message.toString() , Toast.LENGTH_SHORT).show()
                        response.body()?.result?.let {
                            categoryAdapter.submitList(it)
                            binding.rvCategory.adapter = categoryAdapter
                        }
                    }else{
                        Toast.makeText(context, response.body()?.message.toString() + response.errorBody() , Toast.LENGTH_SHORT).show()
                    }
                    binding.pbLoading.visibility = View.GONE
                }
            }catch (e: Exception) {
                Log.d(" Error Category ", e.toString())
                withContext(Dispatchers.Main) {
                    Toast.makeText(context,"Internet Connection is not stable!!", Toast.LENGTH_SHORT).show()
                    binding.pbLoading.visibility = View.GONE
                }
            }
        }
    }

    private fun loadProduct(){
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = productApi.getProductList("Bearer ${Utils.token()}").awaitResponse()
                withContext(Dispatchers.Main){
                    Log.d("Product List: ",  response.toString())
                    if (response.body()?.success == true){
                        Toast.makeText(context, response.body()?.message.toString() , Toast.LENGTH_SHORT).show()
                        response.body()?.result?.let {
                            productAdapter.submitList(it)
                            binding.rvProducts.adapter = productAdapter
                        }
                    }else{
                        Toast.makeText(context, response.body()?.message.toString() + response.errorBody() , Toast.LENGTH_SHORT).show()
                    }
                    binding.pbLoading.visibility = View.GONE
                }
            }catch (e: Exception) {
                Log.d(" Error Product ", e.toString())
                withContext(Dispatchers.Main) {
                    Toast.makeText(context,"Internet Connection is not stable!!", Toast.LENGTH_SHORT).show()
                    binding.pbLoading.visibility = View.GONE
                }
            }
        }
    }

}