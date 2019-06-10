package com.rdd.finy.data

import android.app.admin.DevicePolicyManager
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface WalletDao {

    @Query("SELECT * FROM Wallet WHERE id=:id")
    fun findById(id: Int): LiveData<Wallet>

    @Query("SELECT * FROM Wallet")
    fun findAll(): LiveData<List<Wallet>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(wallet: Wallet): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(wallet: Wallet): Int

    @Delete
    fun delete(wallet: Wallet): Int

}
