package com.rdd.finy.di

import com.rdd.finy.activities.InfoActivity
import com.rdd.finy.fragments.WalletFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(infoActivity: InfoActivity)
    fun inject(walletFragment: WalletFragment)
}