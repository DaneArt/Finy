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

    @Query("SELECT balance FROM Wallet WHERE isActive = 1")
    fun getActiveWalletsBalances(): Flowable<LongArray>

    @Query("SELECT * FROM Wallet WHERE id=:id")
    override fun getById(id: Long): Single<Wallet>

    @Query("SELECT * FROM Wallet")
    override fun getAll(): Flowable<List<Wallet>>

}
