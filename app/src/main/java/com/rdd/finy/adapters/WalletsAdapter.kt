package com.rdd.finy.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.rdd.finy.data.Wallet
import com.rdd.finy.fragments.WalletFragment

class WalletsAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private var walletsList:ArrayList<Wallet> = arrayListOf()

    fun setupWallets(wallets: List<Wallet>) {
        walletsList.clear()
        walletsList.addAll(wallets)
        notifyDataSetChanged()
    }

    override fun getItem(pos: Int): Fragment {
        val walletId = walletsList[pos].id
        return WalletFragment.newInstance(walletId)
    }

    override fun getCount(): Int {
        return walletsList.size
    }
}