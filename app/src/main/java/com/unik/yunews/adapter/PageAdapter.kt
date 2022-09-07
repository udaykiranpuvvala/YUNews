package com.unik.yunews.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.unik.yunews.fragment.CategoryFragment
import com.unik.yunews.fragment.FeedFrament
import com.unik.yunews.fragment.WebFragment

class PageAdapter(fm:FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 3;
    }

    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> {
                return CategoryFragment()
            }
            1 -> {
                return FeedFrament()
            }
            2 -> {
                return WebFragment()
            }
            else -> {
                return FeedFrament()
            }
        }
    }

}
