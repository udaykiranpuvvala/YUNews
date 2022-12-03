package com.unik.yunews.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.unik.yunews.R
import com.unik.yunews.Utility
import com.unik.yunews.adapter.VerticalViewPager
import com.unik.yunews.adapter.ViewPagerAdapter
import com.unik.yunews.api.NewsService
import com.unik.yunews.api.RetorfitHelper
import com.unik.yunews.databinding.FragmentFeedFramentBinding
import com.unik.yunews.models.Article
import com.unik.yunews.models.Source
import com.unik.yunews.repository.NewsRepository
import com.unik.yunews.utilities.Constants
import com.unik.yunews.viewmodel.MainViewModel
import com.unik.yunews.viewmodel.MainViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FeedFrament.newInstance] factory method to
 * create an instance of this fragment.
 */
class FeedFrament : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var feedFragmentBinding: FragmentFeedFramentBinding
    private lateinit var viewModel: MainViewModel
    var clickEventInt = 2

    private val TAG = "Home_Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        feedFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_feed_frament, container, false)
        initUI()
        return feedFragmentBinding.root
    }

    private fun initUI() {
        Log.d(TAG, "initUI: in feed fragment")
        val newsService = RetorfitHelper.getInstance().create(NewsService::class.java)
        val repository = NewsRepository(newsService)
        viewModel = ViewModelProvider(requireActivity(), MainViewModelFactory(repository)).get(MainViewModel::class.java)

        if(Utility.isValueNullOrEmpty(Utility.getSharedPreference(requireContext(),Constants.POST_KEY))){
            Log.d(TAG, "initUI: callIndonesiaLatest")
            viewModel.callIndonesiaLatest()
        }else{
            Log.d(TAG, "initUI: callIndonesiaSearchLatest")
            viewModel.callIndonesiaSearchLatest(Utility.getSharedPreference(requireContext(),Constants.POST_KEY))
        }

        viewModel.news.observe(viewLifecycleOwner) {  articleList->
//            if (articleList != null){
                val listOfArticles= ArrayList<Article>()
                if (articleList != null) {
                    listOfArticles.addAll(articleList.articles)
                    var i = 0
                    listOfArticles.forEach {
                        i++
                        if (i == 1) {
                            val source = Source("Ad", "Ad")
                            val article = Article("Ad", "Ad", "Ad", "Ad",
                                source, "Ad", "Ad", "yunews_ad_01")
                            listOfArticles[3] = article
                        }
                        if (i == 6) {
                            val source = Source("Ad", "Ad")
                            val article = Article("Ad", "Ad", "Ad", "Ad",
                                source, "Ad", "Ad", "yunews_ad_02")
                            listOfArticles[6] = article
                        }
                        if (i == 9) {
                            val source = Source("Ad", "Ad")
                            val article = Article("Ad", "Ad", "Ad", "Ad", source,
                                    "Ad", "Ad", "yunews_ad_03")
                            listOfArticles[9] = article
                        }
                    }
                } else {
                    val source = Source("Ad", "Ad")
                    val article = Article("Ad", "Ad", "Ad", "Ad", source, "Ad", "Ad", "yunews_ad_01")
                    listOfArticles.add(article)
                }

                feedFragmentBinding.verticalViewPager.setAdapter(ViewPagerAdapter(requireContext(), listOfArticles,{
                    if(clickEventInt % 2 == 0){
                        feedFragmentBinding.lnrLytBottom.visibility = View.VISIBLE
                        feedFragmentBinding.rlFeed.visibility = View.VISIBLE
                    }else{
                        feedFragmentBinding.lnrLytBottom.visibility = View.GONE
                        feedFragmentBinding.rlFeed.visibility = View.GONE
                    }
                    clickEventInt++
                },{positionVal ->
                    articleList.articles[positionVal]

                    try {
                        val currentPos = (positionVal - 1)
                        Log.e("Content", "url String FeedFragment:::::::::::: $currentPos")
                        Log.e("Content", "url String FeedFragment:::::::::::: " + articleList.articles[currentPos].url)

                        Utility.setSharedPrefStringData(context,"WebUrl",articleList.articles[currentPos].url)
                        viewModel.setWebString(articleList.articles[currentPos].url)
                    }catch (e: Exception){

                        Log.e("Content", "url String :::::::::::: ${e.localizedMessage}")
                    }
                }))
//            }else {
//                Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
//            }
        }

        feedFragmentBinding.txtCategory.setOnClickListener {
            viewModel.setPosition(1)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FeedFrament.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FeedFrament().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}