package com.rdd.finy.presenters

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.rdd.finy.data.Wallet
import com.rdd.finy.data.WalletDao
import com.rdd.finy.helpers.FinyApp
import com.rdd.finy.views.InfoView
import javax.inject.Inject

@InjectViewState
class InfoPresenter : MvpPresenter<InfoView>() {

    /*@Inject
    lateinit var walletDao: WalletDao


    fun loadWallets(){

        run{
           if (walletDao.findAll().isNotEmpty())
           {
               val walletsList = walletDao.findAll()
               viewState.setupWalletsList(wallets = walletsList)
           }else{
               viewState.setupEmptyWalletsList()
           }
        }
    }*/

    fun testload(){
        viewState.setupEmptyWalletsList()

        val wallets = arrayListOf<Wallet>()
        wallets.add(Wallet(id = 0,title = "First",budjet =  100,colorId = android.R.color.holo_blue_dark))
        wallets.add(Wallet(id = 1,title = "Second",budjet = 0))
        wallets.add(Wallet(id = 2))

        viewState.setupWalletsList(wallets)
    }

}