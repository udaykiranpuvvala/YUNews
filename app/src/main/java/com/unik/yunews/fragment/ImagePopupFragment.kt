package com.unik.yunews.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.unik.yunews.R
import com.unik.yunews.databinding.FragmentImagePopupBinding

class ImagePopupFragment : androidx.fragment.app.DialogFragment() {

    private lateinit var binding : FragmentImagePopupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        binding = FragmentImagePopupBinding.inflate(inflater, container, false)
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_image_popup, container, false)
        initUI()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun initUI() {
        val imageUrl = ""
        Glide.with(this).load(imageUrl).into(binding.popUpImg)
    }

}