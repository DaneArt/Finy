package com.rdd.finy.data.repositories

import io.reactivex.Flowable
import io.reactivex.Single

interface Repository<TEntity> {

    fun getAll(): Flowable<List<TEntity>>
    fun getById(id: Long): Single<TEntity>
    fun update(entity: TEntity)
    fun update(entities: List<TEntity>)
    fun insert(entity: TEntity)
    fun delete(entity: TEntity)

}