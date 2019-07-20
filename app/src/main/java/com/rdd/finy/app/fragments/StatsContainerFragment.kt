package com.rdd.finy.app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.rdd.finy.R
import moxy.MvpAppCompatFragment

class StatsContainerFragment: MvpAppCompatFragment(){

    private lateinit var statsViewPager: ViewPager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_statistic_container,null)

        statsViewPager = view.findViewById(R.id.vp_stats)

        statsViewPager.adapter = object : FragmentStatePagerAdapter(fragmentManager!!){
            override fun getItem(position: Int): Fragment {
                return BalanceChartFragment()
            }

            override fun getCount(): Int {
                return 1
            }
        }

        return view
    }
}