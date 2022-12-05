package com.unik.yunews.activites

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.unik.yunews.R
import com.unik.yunews.Utility
import com.unik.yunews.adapter.PageAdapter
import com.unik.yunews.api.NewsService
import com.unik.yunews.api.RetorfitHelper
import com.unik.yunews.databinding.ActivityHomeBinding
import com.unik.yunews.interfaces.OnSlideView
import com.unik.yunews.repository.NewsRepository
import com.unik.yunews.utilities.PopUtils
import com.unik.yunews.viewmodel.MainViewModel
import com.unik.yunews.viewmodel.MainViewModelFactory

class HomeActivity : AppCompatActivity(), OnSlideView {
    lateinit var phno: String
    lateinit var binding : ActivityHomeBinding
    lateinit var viewModel: MainViewModel

    private val TAG = "Home_Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        Log.d(TAG, "onCreate: Home Activity")
        initUI()
    }

    private fun initUI() {
        if (intent.hasExtra("phno")) {
            phno = intent.getStringExtra("phno").toString()
            Utility.setSharedPrefStringData(this, "PHNO", phno)
        }

        val newsService = RetorfitHelper.getInstance().create(NewsService::class.java)
        val repository = NewsRepository(newsService)
        viewModel = ViewModelProvider(this, MainViewModelFactory(repository)).get(
            MainViewModel::class.java)

        binding.viewPager.adapter = PageAdapter(supportFragmentManager)
//        binding.viewPager.setCurrentItem(1,true)

        viewModel.setPosition(1)

        viewModel.position.observe(this, Observer {
            binding.viewPager.setCurrentItem(it,true)
        })

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
                binding.viewPager.setCurrentItem(1,true)
            }
        }
    }

    override fun movePosition(positionValue: Int) {
        binding.viewPager.setCurrentItem(positionValue,false)
    }
}