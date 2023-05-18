package com.unik.yunews.activites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.messaging.FirebaseMessaging
import com.unik.yunews.R
import com.unik.yunews.Utility
import com.unik.yunews.api.NewsService
import com.unik.yunews.api.RetorfitHelper
import com.unik.yunews.databinding.ActivityLanguageSelectionBinding
import com.unik.yunews.repository.NewsRepository
import com.unik.yunews.viewmodel.MainViewModel
import com.unik.yunews.viewmodel.MainViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.logging.Logger

class LanguageSelectionActivity : AppCompatActivity() {

    lateinit var binding: ActivityLanguageSelectionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_language_selection)

        addDeviceToken()
        binding.txtLangEng.setOnClickListener {
            binding.ivCheckEnglish.visibility = View.VISIBLE
            binding.ivCheckHindi.visibility = View.GONE

            navigateToLogin("English")
        }
        binding.txtLangHindi.setOnClickListener {
            binding.ivCheckEnglish.visibility = View.GONE
            binding.ivCheckHindi.visibility = View.VISIBLE
            navigateToLogin("Hindi")
        }
    }

    private fun addDeviceToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (!it.isSuccessful) {
                return@addOnCompleteListener
            }
            Log.e("Token","it.result"+it.result)
            val newsService = RetorfitHelper.getInstance().create(NewsService::class.java)
            val repository = NewsRepository(newsService)
            var viewModel = ViewModelProvider(this, MainViewModelFactory(repository)).get(
                MainViewModel::class.java)
            viewModel.callSetToken(it.result)
        }
    }

    private fun navigateToLogin(lang: String) {
        Utility.setSharedPrefStringData(this, "language", lang)
        startActivity(Intent(this, HomeActivity::class.java))
//        startActivity(Intent(this, SignInOptionsActivity::class.java))
    }
}