package com.rdd.finy.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rdd.finy.R
import com.rdd.finy.data.Wallet

class WalletsAdapter(private val context: Context) :
        RecyclerView.Adapter<WalletsAdapter.WalletViewHolder>() {

    private val TAG = WalletsAdapter::class.java.simpleName

    private var walletsSourceList = arrayListOf(Wallet(), Wallet(), Wallet())

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

        private val background: RelativeLayout = itemView.findViewById(R.id.background_wallet)
        private val isActiveRadBtn: RadioButton = itemView.findViewById(R.id.rbtn_wallet_activation)
        private val balanceBar: ProgressBar = itemView.findViewById(R.id.pb_wallet_balance)
        private val titleTxt: TextView = itemView.findViewById(R.id.txt_wallet_title)

        private var localWallet: Wallet = Wallet()

        fun bind(wallet: Wallet) {

            localWallet = wallet

            setupBalanceBarProgress()

            if (localWallet.title != "") titleTxt.text = localWallet.title

            isActiveRadBtn.isChecked = localWallet.isActive

            background.setOnClickListener {
                localWallet.isBalanceShown = !localWallet.isBalanceShown
                callbacks.onUpdateWallet(wallet = localWallet)
                setupViewStateVisibility()
            }

            isActiveRadBtn.setOnClickListener {
                localWallet.isActive = !localWallet.isActive
                callbacks.onUpdateWallet(wallet = localWallet)
                isActiveRadBtn.isChecked = localWallet.isActive
            }

            background.setOnLongClickListener {
                callbacks.onShowSetupWalletDialog(wallet.id)
                true
            }

            setupViewStateVisibility()
        }

        private fun setupBalanceBarProgress() {

            if (localWallet.hasUpperDivider) {
                balanceBar.max = localWallet.upperDivider.toInt()
            } else {
                balanceBar.max = localWallet.balance.toInt()
            }

            balanceBar.progress = localWallet.bottomDivider.toInt()
            balanceBar.secondaryProgress = localWallet.balance.toInt()
        }

        private fun setupViewStateVisibility() {

            if (localWallet.isBalanceShown) {
                setBalanceViewVisible()
            } else {
                setTitleViewVisible()
            }

            setupBalanceBarProgress()
        }

        private fun setBalanceViewVisible() {
            balanceBar.visibility = View.VISIBLE
            isActiveRadBtn.visibility = View.INVISIBLE

            val balanceTitle = if (localWallet.hasUpperDivider) {
                "${localWallet.balance}/${localWallet.upperDivider}"
            } else {
                "${localWallet.balance}"
            }
            titleTxt.text = balanceTitle

        }

        private fun setTitleViewVisible() {
            balanceBar.visibility = View.INVISIBLE
            isActiveRadBtn.visibility = View.VISIBLE
            titleTxt.text = localWallet.title
        }

    }

}