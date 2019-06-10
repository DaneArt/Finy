package com.rdd.finy.di

import com.rdd.finy.activities.MainActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface WalletSubComponent : AndroidInjector<MainActivity> {

    @Subcomponent.Builder abstract class Builder : AndroidInjector.Builder<MainActivity>()
}