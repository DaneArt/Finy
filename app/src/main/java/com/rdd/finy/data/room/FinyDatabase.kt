package com.rdd.finy.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rdd.finy.app.models.Wallet


@Database(entities = [Wallet::class], version = 3, exportSchema = false)
abstract class FinyDatabase : RoomDatabase() {

    abstract fun walletDao(): WalletDao

}
