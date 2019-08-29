/*
Copyright 2019 Daniil Artamonov

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
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

class StatsContainerFragment : MvpAppCompatFragment() {

    private lateinit var statsViewPager: ViewPager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_statistic_container, null)

        statsViewPager = view.findViewById(R.id.vp_stats)

        statsViewPager.adapter = object : FragmentStatePagerAdapter(fragmentManager!!) {
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