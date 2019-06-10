package com.rdd.finy.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.rdd.finy.data.Wallet
import com.rdd.finy.data.WalletDao


@Database(entities = [Wallet::class], version = 1, exportSchema = false)
abstract class FinyDatabase : RoomDatabase() {


    abstract val walletDao: WalletDao

}
