package com.rdd.finy.repository

import android.arch.lifecycle.LiveData
import com.rdd.finy.data.Wallet
import com.rdd.finy.data.WalletDao
import javax.inject.Inject


class WalletDataSourse @Inject
constructor(private val walletDao: WalletDao) : WalletRepository {

    override fun findById(id: Int): LiveData<Wallet> {
        return walletDao.findById(id)
    }

    override fun findAll(): LiveData<List<Wallet>> {
        return walletDao.findAll();
    }

    override fun insert(wallet: Wallet): Long {
        return walletDao.insert(wallet)
    }

    override fun delete(wallet: Wallet): Int {
        return walletDao.delete(wallet)
    }

    // Other methods...
}