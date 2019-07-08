package com.rdd.finy.di

import android.content.Context
import androidx.room.Room
import com.rdd.finy.data.FinyDatabase
import com.rdd.finy.data.WalletDao
import com.rdd.finy.data.WalletRepository
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

    @Provides
    fun provideWalletRepository(walletDao: WalletDao):WalletRepository{
        return WalletRepository(walletDao)
    }

    @Provides
    fun provideWalletDao(database: FinyDatabase): WalletDao{
        return database.walletDao()
    }
}
