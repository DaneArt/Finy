package com.rdd.finy.adapters

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rdd.finy.R
import com.rdd.finy.data.Wallet
import io.reactivex.subjects.PublishSubject


class MoneyWalletsAdapter(private val context: Context) :
        RecyclerView.Adapter<MoneyWalletsAdapter.MoneyWalletViewHolder>() {

    private val TAG = WalletsAdapter::class.java.simpleName

    private var walletsSourceList: ArrayList<Wallet> = arrayListOf()
    private var activeWalletsList: HashMap<Wallet, Double> = hashMapOf()
    private val sizeSubject: PublishSubject<Int> = PublishSubject.create()

    private var userBalance: Double = .0

    fun setUserBalance(balance: Double) {
        userBalance = balance
    }

    fun getActiveWalletsSize(): PublishSubject<Int> {
        return sizeSubject
    }

    fun setupWalletsList(wallets: List<Wallet>) {
        walletsSourceList.clear()
        walletsSourceList.addAll(wallets)
        Log.e(TAG, "Update wallets list")
        notifyDataSetChanged()
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

    fun getActiveWallets(): HashMap<Wallet, Double> {
        return activeWalletsList
    }

    inner class MoneyWalletViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
            RecyclerView.ViewHolder(
                    inflater.inflate(
                            R.layout.item_control_wallet,
                            parent,
                            false)) {

        private val walletCheck: CheckBox = itemView.findViewById(R.id.check_money_wallet)
        private val walletTitleTxt: TextView = itemView.findViewById(R.id.txt_money_wallet_title)
        private val walletMoneyCountEdit: EditText = itemView.findViewById(R.id.edit_money_count)

        fun bind(wallet: Wallet) {

            walletCheck.isChecked = activeWalletsList.contains(wallet)
            walletMoneyCountEdit.isEnabled = walletCheck.isChecked

            walletCheck.setOnCheckedChangeListener { buttonView, isChecked ->
                if (buttonView.isPressed) {
                    if (isChecked) {
                        activeWalletsList[wallet] = .0
                        walletMoneyCountEdit.isEnabled = true
                    } else {
                        activeWalletsList.remove(wallet)
                        walletMoneyCountEdit.isEnabled = false

                    }
                    sizeSubject.onNext(activeWalletsList.size)
                }
            }

            walletMoneyCountEdit.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (s!!.isNotBlank()) {
                        if (s.contains("%")) {
                            activeWalletsList[wallet] =
                                    s.toString().dropLast(1).toDouble() / 100 * userBalance
                        } else {
                            activeWalletsList[wallet] = s.toString().toDouble()
                        }

                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

            })

            if (wallet.title != "") walletTitleTxt.text = wallet.title

        }


    }
}