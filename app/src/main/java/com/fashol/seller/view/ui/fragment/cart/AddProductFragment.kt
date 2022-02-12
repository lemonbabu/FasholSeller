package com.fashol.seller.view.ui.fragment.cart

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fashol.seller.R
import com.fashol.seller.data.api.ApiInterfaces
import com.fashol.seller.data.api.RetrofitClient
import com.fashol.seller.data.model.productdata.ProductDataModel
import com.fashol.seller.data.repository.local.CategoryProductListData
import com.fashol.seller.databinding.FragmentAddProductBinding
import com.fashol.seller.utilits.PopUpFragmentCommunicator
import com.fashol.seller.utilits.Utils
import com.fashol.seller.view.adapter.CategoryAdapter
import com.fashol.seller.view.adapter.ProductAdapter
import kotlinx.coroutines.*
import retrofit2.awaitResponse

@DelicateCoroutinesApi
class AddProductFragment : Fragment(R.layout.fragment_add_product), CategoryAdapter.OnCategoryClickListener, ProductAdapter.OnProductClickListener {

    private lateinit var binding: FragmentAddProductBinding
    private lateinit var fcPopUp: PopUpFragmentCommunicator
    private lateinit var productAdapter: ProductAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private val categoryApi: ApiInterfaces.CategoryListInterface by lazy { RetrofitClient.getCategoryList() }
    private val productApi: ApiInterfaces.ProductListInterface by lazy { RetrofitClient.getProductList() }
    private val productByCategoryApi: ApiInterfaces.ProductListByCategoryInterface by lazy { RetrofitClient.getProductListByCategory() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddProductBinding.bind(view)
        fcPopUp = activity as PopUpFragmentCommunicator

        binding.rvProducts.layoutManager = GridLayoutManager(context, 3)
        productAdapter = ProductAdapter(this)

        binding.rvCategory.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        categoryAdapter = CategoryAdapter(this)


        if(CategoryProductListData.flagCat){
            categoryAdapter.submitList(CategoryProductListData.dataCat)
            binding.rvCategory.adapter = categoryAdapter

        }else{
            binding.pbLoading.visibility = View.VISIBLE
            loadCategory()
        }

        if(CategoryProductListData.flagPro){
            productAdapter.submitList(CategoryProductListData.dataPro)
            binding.rvProducts.adapter = productAdapter
            emptyChecking(CategoryProductListData.dataPro.size)
        } else{
            binding.pbLoading.visibility = View.VISIBLE
            loadProduct()
        }

        binding.etSearchProduct.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                filter(s.toString())
            }
        })

    }

    override fun onCategoryClickListener(id: Int) {
        binding.pbLoading.visibility = View.VISIBLE
        loadProductByCategory(id)
    }

    override fun onProductClickListener(id: Int, name: String, avatar: String) {
        val sharedPreferences = activity?.getSharedPreferences("Cart", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.apply{
            putBoolean("session", true)
            putInt("productId", id)
            putString("productName", name)
            putString("productAvatar", avatar)
        }?.apply()

        fcPopUp.passPopUpData("productDetails")
        binding.rvProducts.isEnabled = false
        binding.rvProducts.isClickable = false
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
                            categoryAdapter.submitList(it)
                            binding.rvCategory.adapter = categoryAdapter
                            CategoryProductListData.flagCat = true
                            CategoryProductListData.dataCat = it
                        }
                    }else{
                        Toast.makeText(context, response.body()?.message.toString() + response.errorBody() , Toast.LENGTH_SHORT).show()
                    }
                    binding.pbLoading.visibility = View.GONE
                }
            }catch (e: Exception) {
                Log.d(" Error Category ", e.toString())
                withContext(Dispatchers.Main) {
                    Toast.makeText(context,"Internet not stable or Server error occur!!", Toast.LENGTH_SHORT).show()
                    binding.pbLoading.visibility = View.GONE
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
                            productAdapter.submitList(it)
                            binding.rvProducts.adapter = productAdapter
                            CategoryProductListData.flagPro = true
                            CategoryProductListData.dataPro = it
                            emptyChecking(it.size)
                        }
                    }else{
                        Toast.makeText(context, response.body()?.message.toString() + response.errorBody() , Toast.LENGTH_SHORT).show()
                    }
                    binding.pbLoading.visibility = View.GONE
                }
            }catch (e: Exception) {
                Log.d(" Error Product ", e.toString())
                withContext(Dispatchers.Main) {
                    Toast.makeText(context,"Internet not stable or Server error occur!!", Toast.LENGTH_SHORT).show()
                    binding.pbLoading.visibility = View.GONE
                }
            }
        }
    }

    private fun loadProductByCategory(id : Int){
        Log.d("Category Id ", id.toString())
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = productByCategoryApi.getProductListByCategory( id, "Bearer ${Utils.token()}").awaitResponse()
                withContext(Dispatchers.Main){
                    Log.d("Product List: ",  response.toString())
                    if (response.body()?.success == true){
                        //Toast.makeText(context, response.body()?.message.toString() , Toast.LENGTH_SHORT).show()
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
                Log.d(" Error Product cat ", e.toString())
                withContext(Dispatchers.Main) {
                    Toast.makeText(context,"Internet not stable or Server error occur!!", Toast.LENGTH_SHORT).show()
                    binding.pbLoading.visibility = View.GONE
                }
            }
        }
    }

    private fun filter(text: String) {
        val filteredList: ArrayList<ProductDataModel.Result> = ArrayList()
        for (item in CategoryProductListData.dataPro) {
            for(i in item.tags){
                if (i.lowercase().contains(text.lowercase()) ) {
                    filteredList.add(item)
                }
            }
        }
        emptyChecking(filteredList.size)
        productAdapter.filterList(filteredList)

    }

    private fun emptyChecking(size: Int){
        if(size == 0){
            binding.tvEmptyProduct.visibility = View.VISIBLE
        } else {
            binding.tvEmptyProduct.visibility = View.GONE
        }
    }

}