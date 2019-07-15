package com.rdd.finy.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rdd.finy.R
import com.rdd.finy.data.Wallet

class MoneyWalletsAdapter(private val context: Context) :
        RecyclerView.Adapter<MoneyWalletsAdapter.MoneyWalletViewHolder>() {

    private val TAG = WalletsAdapter::class.java.simpleName

    private var walletsSourceList:ArrayList<Wallet> = arrayListOf()

    private var activeWalletsList: ArrayList<Wallet> = arrayListOf()

    fun getActiveWallets() : ArrayList<Wallet>{
        return activeWalletsList
    }

    fun setupWalletsList(wallets: List<Wallet>) {
        walletsSourceList.clear()
        walletsSourceList.addAll(wallets)
        notifyDataSetChanged()
        Log.e(TAG,"Data set changed! size ${walletsSourceList.size}")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoneyWalletViewHolder {
        val inflater = LayoutInflater.from(context)
        return MoneyWalletViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return walletsSourceList.size
    }

    override fun onBindViewHolder(holder: MoneyWalletViewHolder, position: Int) {
        val wallet = walletsSourceList[position]
        holder.bind(wallet)
    }

    inner class MoneyWalletViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.item_control_wallet, parent, false)) {

        private val walletCheckBox: CheckBox = itemView.findViewById(R.id.check_money_wallet)
        private val walletTitleTextView: TextView = itemView.findViewById(R.id.txt_money_wallet_title)
        private val walletMoneyCountEditTextView: EditText = itemView.findViewById(R.id.edit_money_count)

        fun bind(wallet: Wallet) {

            walletCheckBox.isChecked = activeWalletsList.contains(wallet)
            walletCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
                if(buttonView.isPressed){
                    if(isChecked){
                        activeWalletsList.add(wallet)
                    }else{
                        activeWalletsList.remove(wallet)
                    }
                }
            }

            if(wallet.title!="")walletTitleTextView.text = wallet.title

        }


    }
}