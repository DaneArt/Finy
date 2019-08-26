package com.rdd.finy.data.repositories

import com.rdd.finy.app.models.Wallet
import com.rdd.finy.data.room.WalletDao
import io.reactivex.Flowable

class WalletRepositoryImpl(private val walletDao: WalletDao) : BaseRepository<WalletDao, Wallet>(walletDao),
    WalletRepository {
    companion object {

        private val TAG = WalletRepositoryImpl::class.java.simpleName
    }

    override fun getAllWalletsBalances(): Flowable<IntArray> {
        return walletDao.getAllWalletsBalances()
    }

    override fun getActiveWalletsBalances(): Flowable<IntArray> {
        return walletDao.getActiveWalletsBalances()
    }

}