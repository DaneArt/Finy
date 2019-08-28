package com.rdd.finy.data.repositories

import com.rdd.finy.app.models.Wallet
import com.rdd.finy.data.room.WalletDao
import io.reactivex.Flowable

open class WalletRepositoryImpl(private val walletDao: WalletDao) : BaseRepository<WalletDao, Wallet>(walletDao),
    WalletRepository {
    companion object {

        private val TAG = WalletRepositoryImpl::class.java.simpleName
    }

    override fun getAllWalletsBalances(): Flowable<LongArray> {
        return walletDao.getAllWalletsBalances()
    }

    override fun getActiveWalletsBalances(): Flowable<LongArray> {
        return walletDao.getActiveWalletsBalances()
    }

}