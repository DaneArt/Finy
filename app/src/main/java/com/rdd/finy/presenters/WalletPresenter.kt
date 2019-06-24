package com.rdd.finy.presenters

import android.arch.lifecycle.LifecycleOwner
import android.os.AsyncTask
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.rdd.finy.data.Wallet
import com.rdd.finy.data.WalletDao
import com.rdd.finy.helpers.FinyApp
import com.rdd.finy.views.WalletView
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject


@InjectViewState
class WalletPresenter(val owner: LifecycleOwner) : MvpPresenter<WalletView>() {

    private val TAG = WalletPresenter::class.java.simpleName

    @Inject
    lateinit var walletDao: WalletDao

    private lateinit var localWallet: Wallet

    fun loadWalletById(walletId: Long) {
        FinyApp.app()?.appComponent()?.inject(this)

        walletDao.findById(walletId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { walletFromDB ->
                    localWallet = walletFromDB
                    setupWallet()
                },
                { error ->
                    Log.e(TAG, error.localizedMessage)
                }
            )

    }


    private fun setupWallet() {

        val name = localWallet.name
        val count = localWallet.budjet
        val colorId = localWallet.backColorId
        viewState.updateViewState(name = name, budjet = count, colorId = colorId)


    }

    fun deleteWallet() {
        object : AsyncTask<Void, Void, Int>() {
            override fun doInBackground(vararg params: Void?): Int {
                walletDao.delete(localWallet)
                Log.e(TAG, "Wallet deleted")
                return 1
            }

        }.execute()
    }

    fun updateWalletState(wName: String, wBudjet: Int, wBackColorId: Int) {
        viewState.endEditingWallet()

        object : AsyncTask<Void, Void, Int>() {
            override fun onPreExecute() {
                localWallet.name = wName
                localWallet.budjet = wBudjet
                localWallet.backColorId = wBackColorId
            }

            override fun doInBackground(vararg params: Void?): Int {
                walletDao.update(localWallet)
                Log.e(TAG, "Wallet updated")
                return 1
            }
        }.execute()
    }

    /*fun testLoad(walletId :Long)
    {

        val wallets = arrayListOf<Wallet>()
        wallets.add(Wallet(id = 0,name = "First",budjet =  100,backColorId = R.color.colorAccent))
        wallets.add(Wallet(id = 1,name = "Second",budjet = 0))
        wallets.add(Wallet(id = 2))
        val wallet = wallets[walletId.toInt()]

        val name = wallet.name
        val count = wallet.budjet
        val backColorId = wallet.backColorId

        viewState.updateViewState(name = name,count = count,backColorId = backColorId)
    }*/

    /* private inner class loadWalletTask : AsyncTask<Long, Wallet, Wallet>() {
         override fun doInBackground(vararg params: Long?): Wallet {

             val wallet = params.let { walletDao.findById(params[0]!!) }
             return wallet
         }

         override fun onPostExecute(result: Wallet?) {
             if (result != null) {
                 val name = result.name
                 val count = result.budjet
                 val backColorId = result.backColorId

                 viewState.updateViewState(name = name, count = count, backColorId = backColorId)
             }
         }
     }*/
}