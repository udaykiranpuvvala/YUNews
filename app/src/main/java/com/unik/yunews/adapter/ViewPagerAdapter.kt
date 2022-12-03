package com.unik.yunews.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.unik.yunews.R
import com.unik.yunews.Utility
import com.unik.yunews.models.Article

class ViewPagerAdapter(val context: Context,val articleList: ArrayList<Article>,val onItemSelected: (View) -> Unit,val onItemViewed: (Int) -> Unit) : PagerAdapter() {

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
        val txtSwipeToLeft = itemView.findViewById<TextView>(R.id.txtSwipeToLeft)
        val textView = itemView.findViewById<TextView>(R.id.textView)

        val ivAd = itemView.findViewById<ImageView>(R.id.ivAd)
        val constraintLayoutNews = itemView.findViewById<ConstraintLayout>(R.id.constraintLayoutNews)
//        val txtFrom = itemView.findViewById<TextView>(R.id.txtFrom)
//        txtFrom.setText(articleList[position].author)

        if(articleList[position].title.equals("Ad")){
            ivAd.visibility = View.VISIBLE
            constraintLayoutNews.visibility = View.GONE
            when(articleList[position].urlToImage){
                "yunews_ad_01" -> {
                    ivAd.setImageDrawable(context.resources.getDrawable(R.drawable.yunews_ad_01))
                }
                "yunews_ad_02" -> {
                    ivAd.setImageDrawable(context.resources.getDrawable(R.drawable.yunews_ad_02))
                }
                "yunews_ad_03" -> {
                    ivAd.setImageDrawable(context.resources.getDrawable(R.drawable.yunews_ad_03))
                }
            }
        }else {
            ivAd.visibility = View.GONE
            constraintLayoutNews.visibility = View.VISIBLE
            if (articleList[position].content != null) {
                val content = articleList[position].content.split("…")
                txtNewsDesc.setText(content[0])
            }
            txtNewsTitle.setText(articleList[position].title)
            textView.setText(articleList[position].title + " \n Tap to view more")

            onItemViewed(position)
            Glide.with(context).load(articleList[position].urlToImage).into(ivNews)
            Glide.with(context).load(articleList[position].urlToImage).into(ivTextBg)

            txtNewsDesc.setOnClickListener {
                onItemSelected(txtNewsDesc)
            }
            txtSwipeToLeft.setOnClickListener {
                onItemSelected(txtNewsDesc)
            }
        }
        container.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}