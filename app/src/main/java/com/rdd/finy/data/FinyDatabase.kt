package com.rdd.finy.data

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Wallet::class], version = 1, exportSchema = false)
abstract class FinyDatabase : RoomDatabase() {

    abstract fun walletDao(): WalletDao

}
