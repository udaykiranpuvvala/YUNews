package com.unik.yunews.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.unik.yunews.R
import com.unik.yunews.Utility
import com.unik.yunews.activites.SearchActivity
import com.unik.yunews.adapter.CategoriesAdapter
import com.unik.yunews.adapter.TopicsAdapter
import com.unik.yunews.databinding.FragmentCategoryBinding
import com.unik.yunews.models.CategoryModel
import com.unik.yunews.utilities.Constants
import com.unik.yunews.viewmodel.MainViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var categoryBinding: FragmentCategoryBinding
//    lateinit var onSlideView: OnSlideView


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
        categoryBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false)
        initUI()
        return categoryBinding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CategoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CategoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun initUI() {

        val viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        categoryBinding.txtFeed.setOnClickListener {
            viewModel.setPosition(1)
        }

        categoryBinding.txtSearch.setOnClickListener {
            startActivity(Intent(requireContext(), SearchActivity::class.java))
        }

        val categoriesList = ArrayList<CategoryModel>()
        categoriesList.add(CategoryModel("My Feed", R.drawable.my_feed_icon))
        categoriesList.add(CategoryModel("All News", R.drawable.all_news_icon))
        categoriesList.add(CategoryModel("Top Stories", R.drawable.top_stories_icon))
        categoriesList.add(CategoryModel("Trending", R.drawable.trending_icon))
        categoriesList.add(CategoryModel("Bookmarks", R.drawable.bookmarks_icon))
        categoriesList.add(CategoryModel("Unread", R.drawable.unread_icon))
        categoryBinding.rvCategories.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        categoryBinding.rvCategories.adapter = CategoriesAdapter(requireContext(), categoriesList)

        val topicsList = ArrayList<String>()
        topicsList.add("Business")
        topicsList.add("Politics")
        topicsList.add("Sports")
        topicsList.add("Technology")
        topicsList.add("Startups")
        topicsList.add("Entertainment")
        topicsList.add("Hatke")
        topicsList.add("International")
        topicsList.add("Automobile")
        topicsList.add("Science")
        topicsList.add("Travel")
        topicsList.add("Miscellaneous")
        topicsList.add("Fashion")
        topicsList.add("Education")
        topicsList.add("Health &\nFitness")
        categoryBinding.rvTopics.layoutManager = GridLayoutManager(context, 3)
        categoryBinding.rvTopics.adapter = TopicsAdapter(requireContext(), topicsList) { key ->
//            onSlideView.movePosition(1)
            Utility.setSharedPrefStringData(requireContext(),Constants.POST_KEY,key)
            viewModel.setPosition(1)
            Toast.makeText(requireContext(), "$key Posts", Toast.LENGTH_SHORT).show()
        }
    }
}