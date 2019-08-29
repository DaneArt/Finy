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
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.rdd.finy.R
import com.rdd.finy.app.models.Wallet
import io.reactivex.subjects.PublishSubject


class MoneyWalletsAdapter(private val context: Context) :
    RecyclerView.Adapter<MoneyWalletsAdapter.MoneyWalletViewHolder>() {

    private val TAG = WalletsAdapter::class.java.simpleName

    private var walletsSourceList: ArrayList<Wallet> = arrayListOf()
    private var activeWalletsList: HashMap<Wallet, Int> = hashMapOf()
    private val sizeSubject: PublishSubject<Int> = PublishSubject.create()

    private var userBalance: Int = 0

    fun setUserBalance(balance: Int) {
        userBalance = balance
    }

    fun getActiveWalletsSize(): PublishSubject<Int> {
        return sizeSubject
    }

    fun getActiveWallets(): HashMap<Wallet, Int> {
        return activeWalletsList
    }

    fun setupWalletsList(wallets: List<Wallet>) {
        walletsSourceList.clear()
        walletsSourceList.addAll(wallets)
        Log.e(TAG, "Update wallets list")
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoneyWalletViewHolder {
        val inflater = LayoutInflater.from(context)
        return MoneyWalletViewHolder(inflater = inflater, parent = parent)
    }

    override fun getItemCount(): Int {
        return walletsSourceList.size
    }

    override fun onBindViewHolder(holder: MoneyWalletViewHolder, position: Int) {
        val wallet = walletsSourceList[position]
        holder.bind(wallet)
    }


    inner class MoneyWalletViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(
            inflater.inflate(
                R.layout.item_control_wallet,
                parent,
                false
            )
        ) {

        init {
            ButterKnife.bind(this, itemView)
        }

        @BindView(R.id.check_money_wallet)
        lateinit var walletCheck: CheckBox

        @BindView(R.id.txt_money_wallet_title)
        lateinit var walletTitleTxt: TextView

        @BindView(R.id.edit_money_count)
        lateinit var walletMoneyCountEdit: EditText

        private lateinit var localWallet: Wallet

        fun bind(wallet: Wallet) {

            localWallet = wallet

            walletCheck.isChecked = activeWalletsList.contains(wallet)
            walletMoneyCountEdit.isEnabled = walletCheck.isChecked

            walletCheck.setOnCheckedChangeListener { buttonView, isChecked ->
                if (buttonView.isPressed) {
                    if (isChecked) {
                        activeWalletsList[wallet] = 0
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
                        configureUserInput(s.toString())
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

            })

            if (wallet.title.isNotBlank()) walletTitleTxt.text = wallet.title
        }

        fun configureUserInput(input: String) {
            try {
                activeWalletsList[localWallet] =
                    when {
                        input.contains("%") ->
                            input.dropLast(1).toInt() / 100 * userBalance

                        input.contains(".") || input.contains(",") ->
                            configureDecimalInput(input)

                        else -> input.toInt()
                    }

            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    context.getString(R.string.wrong_input_for_money_adapter),
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        }

        private fun configureDecimalInput(input: String): Int {
            return 1
        }

    }
}