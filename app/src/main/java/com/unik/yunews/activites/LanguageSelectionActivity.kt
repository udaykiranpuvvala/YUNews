package com.unik.yunews.activites

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.unik.yunews.R
import com.unik.yunews.Utility
import com.unik.yunews.databinding.ActivityLanguageSelectionBinding

class LanguageSelectionActivity : AppCompatActivity() {

    lateinit var binding: ActivityLanguageSelectionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_language_selection)

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

    private fun navigateToLogin(lang: String) {
        Utility.setSharedPrefStringData(this, "language", lang)
        startActivity(Intent(this, LoginActivity::class.java))
    }
}