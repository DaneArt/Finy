package com.rdd.finy.di

import android.arch.persistence.room.Room
import android.content.Context
import com.rdd.finy.data.FinyDatabase
import dagger.Module
import dagger.Provides

import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides fun providesAppContext() = context

    @Provides
    @Singleton
    fun providesFinyDatabase(context: Context): FinyDatabase =
        Room.databaseBuilder(context, FinyDatabase::class.java, "finy-db").build()

    @Provides
    fun providesWalletDao(database: FinyDatabase) = database.walletDao
}
