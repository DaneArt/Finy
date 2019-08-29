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
package com.rdd.finy.app.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rdd.finy.R
import com.rdd.finy.app.models.Wallet
import java.lang.Math.abs

class WalletsAdapter(private val context: Context) :
    RecyclerView.Adapter<WalletsAdapter.WalletViewHolder>() {

    private val TAG = WalletsAdapter::class.java.simpleName

    private var walletsSourceList: ArrayList<Wallet> = arrayListOf()

    private lateinit var callbacks: Callbacks

    interface Callbacks {
        fun onUpdateWallet(wallet: Wallet)
        fun onShowSetupWalletDialog(walletId: Long)
    }

    fun setupWalletsList(wallets: List<Wallet>) {
        walletsSourceList.clear()
        walletsSourceList.addAll(wallets)
        notifyDataSetChanged()
    }

    fun addWalletToList(wallet: Wallet) {
        walletsSourceList.add(wallet)
        notifyItemInserted(walletsSourceList.size - 1)
    }

    fun removeWalletFromList(walletId: Long) {
        val position: Int = walletsSourceList.map { it.id }.indexOf(walletId)
        if (position != -1) {
            walletsSourceList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun updateWalletInList(wallet: Wallet) {
        val position: Int = walletsSourceList.map { it.id }.indexOf(wallet.id)
        if (position != -1) {
            walletsSourceList[position] = wallet
            notifyItemChanged(position, null)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletViewHolder {
        val inflater = LayoutInflater.from(context)
        callbacks = context as Callbacks
        return WalletViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return walletsSourceList.size
    }

    override fun onBindViewHolder(holder: WalletViewHolder, position: Int) {
        val wallet = walletsSourceList[position]

        holder.bind(wallet)
    }

    inner class WalletViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_wallet, parent, false)) {

        var balanceBar: ProgressBar = itemView.findViewById(R.id.pb_wallet_balance)
        var titleText: TextView = itemView.findViewById(R.id.txt_wallet_title)
        var balanceText: TextView = itemView.findViewById(R.id.txt_wallet_balance)

        private lateinit var localWallet: Wallet

        fun bind(wallet: Wallet) {

            localWallet = wallet

            setupBalanceBarProgress()

            balanceBar.progressTintList =
                ColorStateList.valueOf(Color.parseColor(localWallet.backColor.split("|")[0]))
            balanceBar.secondaryProgressTintList =
                ColorStateList.valueOf(Color.parseColor(localWallet.backColor.split("|")[1]))
            balanceBar.progressBackgroundTintList =
                ColorStateList.valueOf(Color.parseColor(localWallet.backColor.split("|")[2]))

            if (localWallet.title.isNotBlank()) titleText.text = localWallet.title

            titleText.setOnLongClickListener {
                callbacks.onShowSetupWalletDialog(wallet.id)
                true
            }

            setupViewState()
        }

        private fun setupBalanceBarProgress() {

            val reserve = if (localWallet.bottomDivider != null) {
                localWallet.bottomDivider!!.toInt()
            } else {
                0
            }

            val activeBalance = abs(reserve) + abs(localWallet.balance.toInt() - reserve)

            val maximumBalance = if (localWallet.upperDivider != null) {
                activeBalance + (localWallet.upperDivider!!.toInt() - localWallet.balance.toInt())
            } else {
                activeBalance
            }

            balanceBar.max = maximumBalance

            balanceBar.progress = abs(reserve)

            balanceBar.secondaryProgress = activeBalance

        }

        private fun setupViewState() {

            val balanceTitle = when {
                localWallet.upperDivider != null -> {
                    val balance = if (localWallet.balance % 100 > 0) {
                        "${localWallet.balance / 100}.${localWallet.balance % 100}"
                    } else {
                        "${localWallet.balance / 100}"
                    }

                    val upperDivider = if (localWallet.upperDivider!! % 100 > 0) {
                        "${localWallet.upperDivider!! / 100}.${localWallet.upperDivider!! % 100}"
                    } else {
                        "${localWallet.upperDivider!! / 100}"
                    }

                    "$balance / $upperDivider"
                }

                localWallet.balance % 100 > 0 -> "${localWallet.balance / 100}.${localWallet.balance % 100}"
                else -> "${localWallet.balance / 100}"
            }
            balanceText.text = balanceTitle
        }

    }

}