package com.unik.yunews.activites

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.unik.yunews.R
import com.unik.yunews.Utility

class SplashActivity : AppCompatActivity() {
    val SPLASH_SCREEN_TIME_OUT = 1000;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed(Runnable {
            if(Utility.isValueNullOrEmpty(Utility.getSharedPreference(this, "PHNO"))) {
                Log.d("Home_Activity", "onCreate: language select")
                val i = Intent(this@SplashActivity, LanguageSelectionActivity::class.java)
                startActivity(i)
                finish()
            }else{
                Log.d("Home_Activity", "onCreate: from splash to home activity")
                val i = Intent(this@SplashActivity, HomeActivity::class.java)
                startActivity(i)
                finish()
            }
        }, SPLASH_SCREEN_TIME_OUT.toLong())
    }
}