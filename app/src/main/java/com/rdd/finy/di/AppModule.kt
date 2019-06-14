package com.rdd.finy.di

import android.arch.persistence.room.Room
import android.content.Context
import com.rdd.finy.data.FinyDatabase
import com.rdd.finy.data.WalletDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val context: Context) {

    @Singleton
    @Provides
    fun provideContext():Context{
        return context
    }

    @Singleton
    @Provides
    fun provideFinyDatabase(context:Context):FinyDatabase{
        return Room.databaseBuilder(context,FinyDatabase::class.java,"finy-db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideWalletDao(database: FinyDatabase): WalletDao{
        return database.walletDao()
    }
}
