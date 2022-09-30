package com.luisguzman.newsapp.ui.adapters.viewPager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.luisguzman.newsapp.ui.home.FirstFragment
import com.luisguzman.newsapp.ui.bookmark.SecondFragment
import com.luisguzman.newsapp.utils.AppConstants.NUM_TABS

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
             FirstFragment()
        } else {
             SecondFragment()
        }
    }
}