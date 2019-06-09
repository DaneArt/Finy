package com.rdd.finy.helpers

import dagger.Component
import javax.inject.Singleton

import android.app.Application
import com.rdd.finy.activities.MainActivity
import com.rdd.finy.data.WalletDao
import com.rdd.finy.repository.FinyDatabase
import com.rdd.finy.repository.WalletRepository



@Singleton
@Component(dependencies = [], modules = [AppModule::class, RoomModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun walletDao(): WalletDao

    fun finyDatabase(): FinyDatabase

    fun walletRepository(): WalletRepository

    fun application(): Application

}