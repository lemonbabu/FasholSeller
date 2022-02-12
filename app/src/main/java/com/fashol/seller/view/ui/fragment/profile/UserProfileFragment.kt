package com.fashol.seller.view.ui.fragment.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.fashol.seller.R
import com.fashol.seller.data.repository.local.SellerProfile
import com.fashol.seller.databinding.FragmentUserProfileBinding
import com.fashol.seller.utilits.Utils
import com.squareup.picasso.Picasso

class UserProfileFragment : Fragment(R.layout.fragment_user_profile) {

    private lateinit var binding: FragmentUserProfileBinding
    private var basicInfoFlag = false
    private var locationInfoFlag = false
    private var dueInfoFlag = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserProfileBinding.bind(view)

        if (SellerProfile.flag){
            loadData()
        }

        binding.ibBasicInfo.setOnClickListener {
            if(basicInfoFlag){
                basicInfoFlag = false
                binding.cvBasicInfo.visibility = View.GONE
                binding.ibBasicInfo.setImageResource(R.drawable.ic_material_keyboard_arrow_left)
            }else{
                basicInfoFlag = true
                binding.cvBasicInfo.visibility = View.VISIBLE
                binding.ibBasicInfo.setImageResource(R.drawable.ic_feather_chevron_down)
            }
        }

        binding.ibLocationDetails.setOnClickListener {
            if(locationInfoFlag){
                locationInfoFlag = false
                binding.cvLocationInfo.visibility = View.GONE
                binding.ibLocationDetails.setImageResource(R.drawable.ic_material_keyboard_arrow_left)
            }else{
                locationInfoFlag = true
                binding.cvLocationInfo.visibility = View.VISIBLE
                binding.ibLocationDetails.setImageResource(R.drawable.ic_feather_chevron_down)
            }
        }

        binding.ibDueManagement.setOnClickListener {
            if(dueInfoFlag){
                dueInfoFlag = false
                binding.cvDueInfo.visibility = View.GONE
                binding.ibDueManagement.setImageResource(R.drawable.ic_material_keyboard_arrow_left)
            }else{
                dueInfoFlag = true
                binding.cvDueInfo.visibility = View.VISIBLE
                binding.ibDueManagement.setImageResource(R.drawable.ic_feather_chevron_down)
            }
        }

    }

    private fun loadData(){
        binding.tvUserName.text = SellerProfile.data.name

        binding.tv1Amount.text = SellerProfile.data.profile_dashboard[0].value
        binding.tv1Value.text = SellerProfile.data.profile_dashboard[0].name
        binding.tv2Amount.text = SellerProfile.data.profile_dashboard[1].value
        binding.tv2Value.text = SellerProfile.data.profile_dashboard[1].name
        binding.tv3Amount.text = SellerProfile.data.profile_dashboard[2].value
        binding.tv3Value.text = SellerProfile.data.profile_dashboard[2].name

        binding.tvFullName.text = SellerProfile.data.name
        binding.tvPhone.text = SellerProfile.data.phone
        binding.tvEmail.text = SellerProfile.data.email

        binding.tvAddress.text = SellerProfile.data.status

        val url = Utils.baseUrl() +  SellerProfile.data.avatar
        // load image into view
        Picasso.get().load(url).placeholder(R.drawable.placeholder).into(binding.ivUserAvatar)
    }
}