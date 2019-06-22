package com.rdd.finy.data

import android.arch.persistence.room.*
import io.reactivex.Flowable

@Dao
interface WalletDao {

    @Query("SELECT * FROM Wallet WHERE id=:id")
    fun findById(id: Long): Flowable<Wallet>

    @Query("SELECT * FROM Wallet")
    fun findAll(): Flowable<List<Wallet>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(wallet: Wallet): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(wallet: Wallet): Int

    @Delete
    fun delete(wallet: Wallet): Int

}
