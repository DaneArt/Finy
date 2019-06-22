package com.rdd.finy.presenters

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
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

    private var walletsList : MutableLiveData<List<Wallet>> = MutableLiveData()

    fun loadWalletsFromDB(){
        FinyApp.app()?.appComponent()?.inject(this)

        walletDao.findAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({walletsFromDataBase ->
                walletsList.value = walletsFromDataBase
                viewState.setupEmptyWalletsList()
                walletsList.observe(
                    owner, Observer<List<Wallet>> {
                        Log.e(TAG, "Wallets list changed. Current size is ${walletsList.value!!.size}")
                        updateWalletsListViewState()
                    }
                )
                viewState.endLoadingFromDB()
                updateWalletsListViewState()
            },{ error ->
                Log.e(TAG, error.localizedMessage)
            })

    }

    fun updateWalletsListViewState(){
        viewState.setupWalletsList(walletsList.value!!)

    }

    fun addWallet()
    {
        object:AsyncTask<Void,Void,Int>(){
            override fun doInBackground(vararg params: Void?): Int {
                walletDao.insert(Wallet())
                return 1
            }

            override fun onPostExecute(result: Int?) {
                viewState.setToNewWallet(walletsList.value!!.size)
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