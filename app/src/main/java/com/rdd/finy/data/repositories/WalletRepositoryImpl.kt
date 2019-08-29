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
package com.rdd.finy.data.repositories

import com.rdd.finy.app.models.Wallet
import com.rdd.finy.data.room.WalletDao
import io.reactivex.Flowable

open class WalletRepositoryImpl(private val walletDao: WalletDao) : BaseRepository<WalletDao, Wallet>(walletDao),
    WalletRepository {
    companion object {

        private val TAG = WalletRepositoryImpl::class.java.simpleName
    }

    override fun getAllWalletsBalances(): Flowable<LongArray> {
        return walletDao.getAllWalletsBalances()
    }

    override fun getActiveWallets(): Flowable<List<Wallet>> {
        return walletDao.getActiveWallets()
    }


}