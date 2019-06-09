package com.rdd.finy.repository

import android.arch.lifecycle.LiveData
import com.rdd.finy.data.Wallet


interface WalletRepository {
    fun findById(id: Int): LiveData<Wallet>

    fun findAll(): LiveData<List<Wallet>>

    fun insert(wallet: Wallet): Long

    fun delete(wallet: Wallet): Int

}