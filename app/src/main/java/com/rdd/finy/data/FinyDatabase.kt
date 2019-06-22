package com.rdd.finy.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase


@Database(entities = [Wallet::class], version = 2, exportSchema = false)
abstract class FinyDatabase : RoomDatabase() {


    abstract fun walletDao(): WalletDao

}
