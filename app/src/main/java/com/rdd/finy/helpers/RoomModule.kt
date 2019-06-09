package com.rdd.finy.helpers

import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import android.arch.persistence.room.Room
import android.app.Application
import com.rdd.finy.data.WalletDao
import com.rdd.finy.repository.FinyDatabase
import com.rdd.finy.repository.WalletDataSourse
import com.rdd.finy.repository.WalletRepository


@Module
class RoomModule(mApplication: Application) {

    private val finyDatabase: FinyDatabase =
        Room.databaseBuilder(mApplication, FinyDatabase::class.java, "wallet-db").build()

    @Singleton
    @Provides
    internal fun providesRoomDatabase(): FinyDatabase {
        return finyDatabase
    }

    @Singleton
    @Provides
    internal fun providesProductDao(demoDatabase: FinyDatabase): WalletDao {
        return demoDatabase.walletDao
    }

    @Singleton
    @Provides
    internal fun productRepository(productDao: WalletDao): WalletRepository {
        return WalletDataSourse(productDao)
    }

}