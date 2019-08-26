package com.rdd.finy.app.di

import android.content.Context
import androidx.room.Room
import com.rdd.finy.data.repositories.WalletRepositoryImpl
import com.rdd.finy.data.room.FinyDatabase
import com.rdd.finy.data.room.WalletDao
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
    fun provideFinyDatabase(context:Context): FinyDatabase {
        return Room.databaseBuilder(context, FinyDatabase::class.java,"finy-db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideWalletRepository(walletDao: WalletDao): WalletRepositoryImpl {
        return WalletRepositoryImpl(walletDao)
    }

    @Provides
    fun provideWalletDao(database: FinyDatabase): WalletDao {
        return database.walletDao()
    }
}
