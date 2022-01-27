package com.fashol.seller.view.ui.fragment.cart

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fashol.seller.R
import com.fashol.seller.data.model.customerdata.CustomerDataModel
import com.fashol.seller.data.model.orderdata.OrderDataModel
import com.fashol.seller.data.model.productdata.CategoryDataModel
import com.fashol.seller.data.model.productdata.ProductDataModel
import com.fashol.seller.databinding.FragmentAddProductBinding
import com.fashol.seller.utilits.PopUpFragmentCommunicator
import com.fashol.seller.view.adapter.CategoryAdapter
import com.fashol.seller.view.adapter.CustomerAdapter
import com.fashol.seller.view.adapter.ProductAdapter


class AddProductFragment : Fragment(R.layout.fragment_add_product), CategoryAdapter.OnCustomerClickListener, ProductAdapter.OnProductClickListener {

    private lateinit var binding: FragmentAddProductBinding
    private lateinit var fcpopup: PopUpFragmentCommunicator
    private lateinit var productAdapter: ProductAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private val productData: ArrayList<ProductDataModel> = ArrayList()
    private val categoryData: ArrayList<CategoryDataModel> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddProductBinding.bind(view)
        fcpopup = activity as PopUpFragmentCommunicator

        binding.rvProducts.layoutManager = GridLayoutManager(context, 3)
        productAdapter = ProductAdapter(this)

        binding.rvCategory.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        categoryAdapter = CategoryAdapter(this)

        productData.add(ProductDataModel("1", "Md Rasel", "Md Rasel"))
        productData.add(ProductDataModel("1", "Md Motalib", "Motalib"))
        productData.add(ProductDataModel("1", "Md Rasel", "Ruhul Amin"))
        productData.add(ProductDataModel("1", "Md Rasel", "Mehedi Hasan"))
        productData.add(ProductDataModel("1", "Md Motalib", "Samira Begum"))
        productData.add(ProductDataModel("1", "Md Rasel", "Amjad Hosen"))
        productData.add(ProductDataModel("1", "Md Rasel", "Md Rasel"))
        productData.add(ProductDataModel("1", "Md Motalib", "Motalib"))
        productData.add(ProductDataModel("1", "Md Rasel", "Ruhul Amin"))
        productData.add(ProductDataModel("1", "Md Motalib", "Samira Begum"))
        productData.add(ProductDataModel("1", "Md Rasel", "Amjad Hosen"))
        productData.add(ProductDataModel("1", "Md Rasel", "Md Rasel"))
        productData.add(ProductDataModel("1", "Md Motalib", "Motalib"))

        categoryData.add(CategoryDataModel("1", "Md Rasel", "Md Rasel"))
        categoryData.add(CategoryDataModel("1", "Md Motalib", "Motalib"))
        categoryData.add(CategoryDataModel("1", "Md Rasel", "Ruhul Amin"))
        categoryData.add(CategoryDataModel("1", "Md Rasel", "Mehedi Hasan"))
        categoryData.add(CategoryDataModel("1", "Md Motalib", "Samira Begum"))
        categoryData.add(CategoryDataModel("1", "Md Rasel", "Amjad Hosen"))
        categoryData.add(CategoryDataModel("1", "Md Rasel", "Md Rasel"))
        categoryData.add(CategoryDataModel("1", "Md Motalib", "Motalib"))
        categoryData.add(CategoryDataModel("1", "Md Rasel", "Ruhul Amin"))

        productAdapter.submitList(productData)
        binding.rvProducts.adapter = productAdapter

        categoryAdapter.submitList(categoryData)
        binding.rvCategory.adapter = categoryAdapter

    }

    override fun onCustomerClickListener(id: String, name: String, avatar: String) {
        //
    }

    override fun onProductClickListener(id: String, name: String, avatar: String) {
        val sharedPreferences = activity?.getSharedPreferences("Cart", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.apply{
            putBoolean("session", true)
            putString("productName", name)
            putString("productAvatar", avatar)
        }?.apply()

        fcpopup.passPopUpData("productDetails")
    }

}