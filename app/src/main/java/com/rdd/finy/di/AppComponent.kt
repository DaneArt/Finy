package com.rdd.finy.di

import com.rdd.finy.activities.MainActivity
import dagger.Component

import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, WalletModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)


}
