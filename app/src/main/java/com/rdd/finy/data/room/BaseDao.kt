package com.rdd.finy.data.room

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import io.reactivex.Flowable
import io.reactivex.Single

interface BaseDao<TEntity> {

    fun getById(id: Long): Single<TEntity>
    fun getAll(): Flowable<List<TEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: TEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(entities: List<TEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(entity: TEntity)

    @Delete
    fun delete(entity: TEntity)
}