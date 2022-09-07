package com.unik.yunews.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.unik.yunews.R
import com.unik.yunews.Utility
import com.unik.yunews.models.Article

class ViewPagerAdapter(val context: Context,val articleList: List<Article>) : PagerAdapter() {

    override fun getCount(): Int {
        return articleList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView: View =
            LayoutInflater.from(context).inflate(R.layout.item_container, container, false)
        val ivNews = itemView.findViewById<ImageView>(R.id.ivNews)
        val ivTextBg = itemView.findViewById<ImageView>(R.id.ivTextBg)
        val txtNewsTitle = itemView.findViewById<TextView>(R.id.txtNewsTitle)
        val txtNewsDesc = itemView.findViewById<TextView>(R.id.txtNewsDesc)
        val textView = itemView.findViewById<TextView>(R.id.textView)
        val txtFrom = itemView.findViewById<TextView>(R.id.txtFrom)

        txtFrom.setText(articleList[position].author)
        txtNewsDesc.setText(articleList[position].content)
        txtNewsTitle.setText(articleList[position].title)
        textView.setText(articleList[position].title+" \n Tap to view more")

        Utility.setSharedPrefStringData(context,"WebUrl",articleList[position].url)
        Glide.with(context).load(articleList[position].urlToImage).into(ivNews)
        Glide.with(context).load(articleList[position].urlToImage).into(ivTextBg)
        container.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}