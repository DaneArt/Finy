package com.rdd.finy.di

import android.app.Activity
import com.rdd.finy.activities.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Module(subcomponents = [WalletSubComponent::class])
abstract class WalletModule {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
    internal abstract fun bindsToDoActivityInjectorFactory(builder: WalletSubComponent.Builder): AndroidInjector.Factory<out Activity>
}
