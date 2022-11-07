package com.unik.yunews.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.unik.yunews.R
import com.unik.yunews.Utility
import com.unik.yunews.databinding.FragmentWebBinding
import com.unik.yunews.viewmodel.MainViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WebFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WebFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var binding: FragmentWebBinding

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
    ): View? {
        // Inflate the layout for this fragment
        binding  = DataBindingUtil.inflate(inflater,R.layout.fragment_web, container, false)
        initUI()
        return binding.root
    }

    private fun initUI() {

        val viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        // this will enable the javascript settings
        // WebViewClient allows you to handle
        // onPageFinished and override Url loading.
        binding.webView.webViewClient = WebViewClient()
        binding.webView.settings.javaScriptEnabled = true

        // if you want to enable zoom feature
        binding.webView.settings.setSupportZoom(true)

        Log.e("Content", "url String WebFragment:::::::::::: ${Utility.getSharedPreference(requireContext(),"WebUrl")}")
        Log.e("Content", "url String WebFragment  viewModel :::::::::::: ${viewModel.webUrl.value}")
        viewModel.webUrl.observe(viewLifecycleOwner) { webUrl ->
            Log.e("Content", "url String WebFragment:::::::::::: $webUrl")
            binding.webView.loadUrl(webUrl)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WebFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WebFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}