package com.rdd.finy.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.rdd.finy.data.Wallet
import com.rdd.finy.fragments.WalletFragment


class WalletsAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private var walletsList:ArrayList<Wallet> = arrayListOf()

    private var baseId : Long= 0

    fun setupWallets(wallets: List<Wallet>) {
        walletsList.clear()
        walletsList.addAll(wallets)
        notifyChangeInPosition(position = 1)
        notifyDataSetChanged()
    }

    override fun getItem(pos: Int): Fragment {
        val walletId = walletsList[pos].id
        return WalletFragment.newInstance(walletId)
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    fun notifyChangeInPosition(position: Int) {
        // shift the ID returned by getItemId outside the range of all previous fragments
        baseId += count + position
    }

    override fun getItemId(position: Int): Long {
        return baseId + position
    }

    override fun getCount(): Int {
        return walletsList.size
    }
}