package com.rdd.finy.repository

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.rdd.finy.data.Wallet
import com.rdd.finy.data.WalletDao

@Database(entities = [Wallet::class], version = FinyDatabase.VERSION)
abstract class FinyDatabase : RoomDatabase() {

    abstract val walletDao: WalletDao

    companion object {

        const val VERSION = 1
    }

}
