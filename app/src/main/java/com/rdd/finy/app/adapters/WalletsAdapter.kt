package com.rdd.finy.app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.rdd.finy.R
import com.rdd.finy.app.models.Wallet
import java.lang.Math.abs

class WalletsAdapter(private val context: Context) :
        RecyclerView.Adapter<WalletsAdapter.WalletViewHolder>() {

    private val TAG = WalletsAdapter::class.java.simpleName

    private var walletsSourceList: ArrayList<Wallet> = arrayListOf()
    private var balancedWalletsList: ArrayList<Wallet> = arrayListOf()

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

        init {
            ButterKnife.bind(this, itemView)
        }

        @BindView(R.id.rbtn_wallet_activation)
        lateinit var isActiveRadBtn: RadioButton
        @BindView(R.id.pb_wallet_balance)
        lateinit var balanceBar: ProgressBar
        @BindView(R.id.txt_wallet_title)
        lateinit var titleText: TextView
        @BindView(R.id.txt_wallet_balance)
        lateinit var balanceText: TextView

        private lateinit var localWallet: Wallet

        fun bind(wallet: Wallet) {

            localWallet = wallet

            setupBalanceBarProgress()

            if (localWallet.title.isNotBlank()) titleText.text = localWallet.title

            isActiveRadBtn.isChecked = localWallet.isActive

            titleText.setOnClickListener {
                setupViewStateVisibility()
            }

            isActiveRadBtn.setOnClickListener {
                localWallet.isActive = !localWallet.isActive
                callbacks.onUpdateWallet(wallet = localWallet)
                isActiveRadBtn.isChecked = localWallet.isActive
            }

            titleText.setOnLongClickListener {
                callbacks.onShowSetupWalletDialog(wallet.id)
                true
            }

            setupViewStateVisibility()
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

            balanceBar.progress = abs(reserve)

            balanceBar.secondaryProgress = activeBalance

            balanceBar.max = maximumBalance
        }

        private fun setupViewStateVisibility() {

            if (!balancedWalletsList.contains(localWallet)) {
                setBalanceViewVisible()
            } else {
                setTitleViewVisible()
            }

            setupBalanceBarProgress()
        }

        private fun setBalanceViewVisible() {
            balanceBar.visibility = View.VISIBLE
            balanceText.visibility = View.VISIBLE

            val balanceTitle = if (localWallet.upperDivider != null) {
                val balance = "${localWallet.balance / 100}.${localWallet.balance % 100}"
                val upperDivider = "${localWallet.upperDivider!! / 100}.${localWallet.upperDivider!! % 100}"
                "$balance / $upperDivider"
            } else {
                "${localWallet.balance / 100}.${localWallet.balance % 100}"
            }
            balanceText.text = balanceTitle

            balancedWalletsList.add(localWallet)
        }

        private fun setTitleViewVisible() {
            balanceBar.visibility = View.GONE
            balanceText.visibility = View.GONE

            balancedWalletsList.remove(localWallet)
        }

    }

}