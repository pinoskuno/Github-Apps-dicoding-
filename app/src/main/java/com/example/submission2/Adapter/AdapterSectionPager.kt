package com.example.submission2.Adapter

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.submission2.Fragment.FollowersF
import com.example.submission2.Fragment.FollowingF
import com.example.submission2.R

class AdapterSectionPager(private val mCtx: Context, fm: FragmentManager, data :Bundle ) :FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.tab_followers,R.string.tab_following)

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        var fragment:Fragment? = null
        when(position){
            0-> fragment = FollowersF()
            1-> fragment = FollowingF()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mCtx.resources.getString(TAB_TITLES[position])
    }

    private val fragmentBundle: Bundle
    init {
        fragmentBundle = data
    }
}