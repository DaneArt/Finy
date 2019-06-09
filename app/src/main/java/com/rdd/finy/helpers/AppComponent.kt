package com.rdd.finy.helpers

import android.app.Application
import com.rdd.finy.activities.MainActivity
import com.rdd.finy.data.WalletDao
import com.rdd.finy.repository.FinyDatabase
import com.rdd.finy.repository.WalletRepository
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(dependencies = [], modules = [AppModule::class, RoomModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun productDao(): WalletDao

    fun demoDatabase(): FinyDatabase

    fun productRepository(): WalletRepository

    fun application(): Application

}