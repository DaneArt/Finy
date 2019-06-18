package com.rdd.finy.presenters

import android.os.AsyncTask
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.rdd.finy.data.Wallet
import com.rdd.finy.data.WalletDao
import com.rdd.finy.helpers.FinyApp
import com.rdd.finy.views.InfoView
import javax.inject.Inject

@InjectViewState
class InfoPresenter : MvpPresenter<InfoView>() {



    @Inject
    lateinit var walletDao: WalletDao

    fun loadWallets(){
        FinyApp.app()?.appComponent()?.inject(this)
        viewState.setupEmptyWalletsList()
        loadWalletsTask().execute()
    }

    fun addWallet()
    {
        addWalletTask().execute()
    }

    /*fun testload(){
        viewState.setupEmptyWalletsList()

        val wallets = arrayListOf<Wallet>()
        wallets.add(Wallet(id = 0,title = "First",budjet =  100,colorId = android.R.color.holo_blue_dark))
        wallets.add(Wallet(id = 1,title = "Second",budjet = 0))
        wallets.add(Wallet(id = 2))

        viewState.setupWalletsList(wallets)
    }*/

    private inner class addWalletTask : AsyncTask<Void,Void, List<Wallet>>() {

        override fun doInBackground(vararg params: Void?): List<Wallet>? {

            walletDao.insert(wallet = Wallet())
            return walletDao.findAll()
        }

        override fun onPostExecute(wallets: List<Wallet>?) {
            viewState.setupWalletsList(wallets = wallets!!)
            viewState.setToNewWallet(lastPos = wallets.size-1)
                        }
    }

    private inner class loadWalletsTask : AsyncTask<Void, Void, List<Wallet>>() {

        override fun doInBackground(vararg params: Void?): List<Wallet> {
            return walletDao.findAll()
        }

        override fun onPostExecute(result: List<Wallet>?) {
            if(result !=null) viewState.setupWalletsList(result)
        }
    }


}