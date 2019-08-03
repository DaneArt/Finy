package com.rdd.finy.data.repositories

import android.os.AsyncTask
import com.rdd.finy.app.models.Wallet
import com.rdd.finy.data.room.WalletDao
import io.reactivex.Flowable
import io.reactivex.Single

class WalletRepository(private val walletDao: WalletDao) {
    companion object {

        private val TAG = WalletRepository::class.java.simpleName
    }

    fun getAllWallets(): Flowable<List<Wallet>> {
        return walletDao.getAllWallets()
    }

    fun getWalletById(id: Long): Single<Wallet> {
        return walletDao.getWalletById(id = id)
    }

    fun getAllWalletsBalances(): Flowable<IntArray> {
        return walletDao.getAllWalletsBalances()
    }

    fun getActiveWalletsBalances(): Flowable<IntArray> {
        return walletDao.getActiveWalletsBalances()
    }

    fun updateWallets(wallets: List<Wallet>){
        UpdateWalletsAsyncTask(walletDao = walletDao).execute(wallets)
    }

    fun insertWallet(wallet: Wallet) {
        InsertWalletAsyncTask(walletDao = walletDao).execute(wallet)
    }

    fun updateWallet(wallet: Wallet) {
        UpdateWalletAsyncTask(walletDao = walletDao).execute(wallet)
    }

    fun deleteWallet(wallet: Wallet) {
        DeleteWalletAsyncTask(walletDao = walletDao).execute(wallet)
    }

    private class UpdateWalletsAsyncTask(val walletDao: WalletDao) : AsyncTask<List<Wallet>, Unit, Unit>() {
        override fun doInBackground(vararg wallets: List<Wallet>?) {
            walletDao.update(wallets[0]!!)
        }
    }

    private class InsertWalletAsyncTask(val walletDao: WalletDao) : AsyncTask<Wallet, Unit, Unit>() {
        override fun doInBackground(vararg wallet: Wallet?) {
            walletDao.insert(wallet[0]!!)
        }
    }

    private class UpdateWalletAsyncTask(val walletDao: WalletDao) : AsyncTask<Wallet, Unit, Unit>() {
        override fun doInBackground(vararg wallet: Wallet?) {
            walletDao.update(wallet[0]!!)
        }
    }

    private class DeleteWalletAsyncTask(val walletDao: WalletDao) : AsyncTask<Wallet, Unit, Unit>() {
        override fun doInBackground(vararg wallet: Wallet?) {
            walletDao.delete(wallet[0]!!)
        }
    }

}