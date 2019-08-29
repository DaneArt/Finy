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
package com.rdd.finy.data.room

import androidx.room.Dao
import androidx.room.Query
import com.rdd.finy.app.models.Wallet
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface WalletDao : BaseDao<Wallet> {

    @Query("SELECT balance FROM Wallet")
    fun getAllWalletsBalances(): Flowable<LongArray>

    @Query("SELECT * FROM Wallet WHERE isActive = 1")
    fun getActiveWallets(): Flowable<List<Wallet>>

    @Query("SELECT * FROM Wallet WHERE id=:id")
    override fun getById(id: Long): Single<Wallet>

    @Query("SELECT * FROM Wallet")
    override fun getAll(): Flowable<List<Wallet>>

}
