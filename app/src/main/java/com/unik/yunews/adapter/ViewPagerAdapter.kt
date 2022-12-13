package com.unik.yunews.adapter

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.unik.yunews.R
import com.unik.yunews.activites.NewsImageActivity
import com.unik.yunews.api.NewsService
import com.unik.yunews.api.RetorfitHelper
import com.unik.yunews.models.Article
import com.unik.yunews.repository.NewsRepository
import com.unik.yunews.viewmodel.MainViewModel
import com.unik.yunews.viewmodel.MainViewModelFactory
import retrofit2.create

class ViewPagerAdapter(val context: Context, private val activity : FragmentActivity, val articleList: ArrayList<Article>, val onItemSelected: (View) -> Unit, val onItemViewed: (Int) -> Unit) : PagerAdapter() {

    private var viewModel : MainViewModel

    init{
        val newsService : NewsService = RetorfitHelper.getInstance().create()
        val repository = NewsRepository(newsService)
        viewModel = ViewModelProvider(activity, MainViewModelFactory(repository)).get(MainViewModel::class.java)
    }

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
//                    ivAd.setOnClickListener{
//                        val intent = Intent()
//                        intent.action = Intent.ACTION_VIEW
//                        intent.addCategory(Intent.CATEGORY_BROWSABLE)
//                        intent.data = Uri.parse("https://royalmaxxx.com/")
//                        startActivity(intent)
//                    }
                }
                "yunews_ad_02" -> {
                    ivAd.setImageDrawable(context.resources.getDrawable(R.drawable.yunews_ad_02))
                }
                "yunews_ad_03" -> {
                    ivAd.setImageDrawable(context.resources.getDrawable(R.drawable.yunews_ad_03))
                }
            }
            ivAd.setOnClickListener {
                when(articleList[position].urlToImage){
                    "yunews_ad_01" -> {
                        viewModel.setWebString("https://royalmaxxx.com/")
                    }
                    "yunews_ad_02" -> {
                        viewModel.setWebString("https://mustzlm.com/")
                    }
                    "yunews_ad_03" -> {
                        viewModel.setWebString("http://kampungtoto88.com/")
                    }
                }
                viewModel.setPosition(2)
            }
        }else {
            ivAd.visibility = View.GONE
            constraintLayoutNews.visibility = View.VISIBLE
            if (articleList[position].content != null) {
                val content = articleList[position].content.split("…")
                txtNewsDesc.setText(content[0]+"...")
            }else{
                txtNewsDesc.setText(articleList[position].description)
            }

            txtNewsTitle.setText(articleList[position].title)
            textView.setText(articleList[position].title + " \n Tap to view more")
            var urlSplit = articleList[position].url.split(".co")
            if(urlSplit.size >= 1){
                val finalUrl = urlSplit[0].removePrefix("https://www.")
                if(finalUrl.contains("https://")){
                    finalUrl.removePrefix("https://")
                }
                Log.e("Removed String","Suffix Prefix::::::: "+finalUrl)
                txtSwipeToLeft.text = "Swipe left to redirect the website story to $finalUrl"
            }

            onItemViewed(position)
            Glide.with(context).load(articleList[position].urlToImage).into(ivNews)
            Glide.with(context).load(articleList[position].urlToImage).into(ivTextBg)

            txtNewsDesc.setOnClickListener {
                onItemSelected(txtNewsDesc)
            }
            txtSwipeToLeft.setOnClickListener {
                onItemSelected(txtNewsDesc)
            }

            textView.setOnClickListener{
                viewModel.setWebString(articleList[position].url)
                viewModel.setPosition(2)
            }
            ivNews.setOnClickListener{
                val intent = Intent(context,NewsImageActivity::class.java)
                intent.putExtra("imageUrl",articleList[position].urlToImage)
                context.startActivity(intent)

               /* val builder = Dialog(context)
                builder.requestWindowFeature(Window.FEATURE_NO_TITLE)

                val imageView = ImageView(context)
                Glide.with(context).load(articleList[position].urlToImage).into(imageView)

                builder.addContentView(
                    imageView, RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                )
                builder.show()*/

//                val builder : AlertDialog.Builder = AlertDialog.Builder(activity)
            }
        }
        container.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}