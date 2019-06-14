package com.rdd.finy.presenters

import android.graphics.Color
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.rdd.finy.R
import com.rdd.finy.data.Wallet
import com.rdd.finy.views.WalletView
import javax.inject.Inject


@InjectViewState
class WalletPresenter : MvpPresenter<WalletView>() {


    /*fun loadWalletById(walletId: Long) {



    }*/

    fun testLoad(walletId :Long)
    {

        val wallets = arrayListOf<Wallet>()
        wallets.add(Wallet(id = 0,title = "First",budjet =  100,colorId = R.color.colorAccent))
        wallets.add(Wallet(id = 1,title = "Second",budjet = 0))
        wallets.add(Wallet(id = 2))
        val wallet = wallets[walletId.toInt()]

        val name = wallet.title
        val count = wallet.budjet
        val colorId = wallet.colorId

        viewState.setupViews(name = name,count = count,colorId = colorId)
    }

}