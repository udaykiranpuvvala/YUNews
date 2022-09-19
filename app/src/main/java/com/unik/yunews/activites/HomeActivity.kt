package com.unik.yunews.activites

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.unik.yunews.R
import com.unik.yunews.Utility
import com.unik.yunews.adapter.PageAdapter
import com.unik.yunews.databinding.ActivityHomeBinding
import com.unik.yunews.utilities.PopUtils

class HomeActivity : AppCompatActivity() {
    lateinit var phno: String
    lateinit var binding: ActivityHomeBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        initUI()
    }

    private fun initUI() {
        if (intent.hasExtra("phno")) {
            phno = intent.getStringExtra("phno").toString()
            Utility.setSharedPrefStringData(this, "PHNO", phno)
        }


        binding.viewPager.adapter = PageAdapter(supportFragmentManager)
        binding.viewPager.setCurrentItem(1,false)

    }

    override fun onBackPressed() {
        when (binding.viewPager.currentItem) {
            0 -> {
                PopUtils.exitDialog(this, "Are you sure want to Exit?", View.OnClickListener {
                    finishAffinity()
                })
            }
            1 -> {
                PopUtils.exitDialog(this,"Are you sure want to Exit?", View.OnClickListener {
                    finishAffinity()
                })
            }
            2 -> {
                binding.viewPager.setCurrentItem(1,false)
            }
        }
    }
}