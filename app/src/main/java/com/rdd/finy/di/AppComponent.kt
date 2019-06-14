package com.rdd.finy.di

import com.rdd.finy.activities.InfoActivity
import com.rdd.finy.fragments.WalletFragment
import com.rdd.finy.presenters.InfoPresenter
import com.rdd.finy.presenters.WalletPresenter
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(infoPresenter: InfoPresenter)
    fun inject(walletPresenter: WalletPresenter)
}