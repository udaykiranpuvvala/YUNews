package com.unik.yunews

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.unik.yunews.adapter.ViewPagerAdapter
import com.unik.yunews.api.NewsService
import com.unik.yunews.api.RetorfitHelper
import com.unik.yunews.databinding.ActivityHomeBinding
import com.unik.yunews.repository.NewsRepository
import com.unik.yunews.viewmodel.MainViewModel
import com.unik.yunews.viewmodel.MainViewModelFactory

class HomeActivity : AppCompatActivity() {
    lateinit var phno: String
    lateinit var binding: ActivityHomeBinding

    private lateinit var viewModel: MainViewModel


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
        val newsService = RetorfitHelper.getInstance().create(NewsService::class.java)
        val repository = NewsRepository(newsService)
        viewModel = ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)

        viewModel.news.observe(this) {  articleList->
            if (articleList != null){
                binding.verticalViewPager.setAdapter(ViewPagerAdapter(this@HomeActivity, articleList.articles))
            }else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }

    }
}