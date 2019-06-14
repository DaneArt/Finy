package com.rdd.finy.presenters

import android.os.AsyncTask
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.rdd.finy.data.Wallet
import com.rdd.finy.data.WalletDao
import com.rdd.finy.helpers.FinyApp
import com.rdd.finy.views.WalletView
import javax.inject.Inject


@InjectViewState
class WalletPresenter : MvpPresenter<WalletView>() {

    @Inject
    lateinit var walletDao: WalletDao

    fun loadWalletById(walletId: Long) {
        FinyApp.app()?.appComponent()?.inject(this)

        loadWalletTask().execute(walletId)
    }

    /*fun testLoad(walletId :Long)
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
    }*/

    private inner class loadWalletTask : AsyncTask<Long, Wallet, Wallet>() {
        override fun doInBackground(vararg params: Long?): Wallet {

            val wallet = params.let { walletDao.findById(params[0]!!) }
            return wallet
        }

        override fun onPostExecute(result: Wallet?) {
            if (result != null) {
                val name = result.title
                val count = result.budjet
                val colorId = result.colorId

                viewState.setupViews(name = name, count = count, colorId = colorId)
            }
        }
    }
}