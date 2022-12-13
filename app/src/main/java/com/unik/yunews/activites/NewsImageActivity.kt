package com.unik.yunews.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import com.unik.yunews.R

class NewsImageActivity : AppCompatActivity() {
    lateinit var ivNewsLarge: ImageView
    lateinit var ivClose: ImageView
    lateinit var imageUrl: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_image)
        imageUrl = intent.getStringExtra("imageUrl").toString()
        initUI()
    }

    private fun initUI() {
        ivNewsLarge = findViewById(R.id.ivNewsLarge)
        ivClose = findViewById(R.id.ivClose)
        if(imageUrl.isNotBlank()){
//            Picasso.get().load(imageUrl).into(ivNewsLarge)
            Glide.with(this).load(imageUrl).error(R.drawable.ic_yu_news).into(ivNewsLarge)
        }

        ivClose.setOnClickListener {
            finish()
        }
    }
}