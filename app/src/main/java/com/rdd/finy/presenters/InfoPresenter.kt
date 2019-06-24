package com.rdd.finy.presenters

import android.arch.lifecycle.LifecycleOwner
import android.os.AsyncTask
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.rdd.finy.data.Wallet
import com.rdd.finy.data.WalletDao
import com.rdd.finy.helpers.FinyApp
import com.rdd.finy.views.InfoView
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@InjectViewState
class InfoPresenter(val owner: LifecycleOwner) : MvpPresenter<InfoView>() {

    private val TAG = InfoPresenter::class.java.simpleName

    @Inject
    lateinit var walletDao: WalletDao

    private var localWalletsList: ArrayList<Wallet> = arrayListOf()

    fun loadWalletsFromDB() {
        FinyApp.app()?.appComponent()?.inject(this)

        viewState.setupEmptyWalletsList()

        walletDao.findAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ walletsFromDataBase ->
                localWalletsList.clear()
                localWalletsList.addAll(walletsFromDataBase)
                Log.e(TAG, "Wallets list changed. Current size is ${localWalletsList.size}")
                for(item in localWalletsList){
                    Log.e(TAG,"Wallet id ${item.id}")
                }
                updateWalletsListViewState()
            }, { error ->
                Log.e(TAG, error.localizedMessage)
            })

    }

    fun updateWalletsListViewState() {
        viewState.setupWalletsList(localWalletsList)
    }

    fun addWallet() {
        object : AsyncTask<Void, Void, Int>() {
            override fun doInBackground(vararg params: Void?): Int {
                walletDao.insert(Wallet())
                return 1
            }

            override fun onPostExecute(result: Int?) {
                viewState.setToNewWallet(localWalletsList.size - 1)
                updateWalletsListViewState()
            }

        }.execute()
    }

    /*fun testload(){
        viewState.setupEmptyWalletsList()

        val wallets = arrayListOf<Wallet>()
        wallets.add(Wallet(id = 0,name = "First",budjet =  100,backColorId = android.R.color.holo_blue_dark))
        wallets.add(Wallet(id = 1,name = "Second",budjet = 0))
        wallets.add(Wallet(id = 2))

        viewState.setupWalletsList(wallets)
    }*/

    /*  private inner class addWalletTask : AsyncTask<Void,Void, List<Wallet>>() {

          override fun doInBackground(vararg params: Void?): List<Wallet>? {

              walletDao.insert(wallet = Wallet())
              return walletDao.findAll()
          }

          override fun onPostExecute(wallets: List<Wallet>?) {
              viewState.setupWalletsList(wallets = wallets!!)
              viewState.setToNewWallet(lastPos = wallets.size-1)
                          }
      }*/

    /*private inner class loadWalletsTask : AsyncTask<Void, Void, List<Wallet>>() {

        override fun doInBackground(vararg params: Void?): List<Wallet> {
            return walletDao.findAll()
        }

        override fun onPostExecute(result: List<Wallet>?) {
            if(result !=null) viewState.setupWalletsList(result)
        }
    }*/


}