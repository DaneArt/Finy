/*
Copyright 2019 Daniil Artamonov

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
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
