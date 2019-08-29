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

import android.annotation.SuppressLint
import android.os.AsyncTask
import com.rdd.finy.data.room.BaseDao
import io.reactivex.Flowable
import io.reactivex.Single

open class BaseRepository<TDao : BaseDao<TEntity>, TEntity>(private val dao: TDao) : Repository<TEntity> {

    override fun getAll(): Flowable<List<TEntity>> {
        return dao.getAll()
    }

    override fun getById(id: Long): Single<TEntity> {
        return dao.getById(id = id)
    }

    override fun update(entity: TEntity) {
        UpdateEntityAsyncTask(dao).execute(entity)
    }

    override fun update(entities: List<TEntity>) {
        UpdateEntitiesAsyncTask(dao).execute(entities)
    }


    override fun insert(entity: TEntity) {
        InsertEntityAsyncTask(dao).execute(entity)
    }

    override fun delete(entity: TEntity) {
        DeleteEntityAsyncTask(dao).execute(entity)
    }

    @SuppressLint("StaticFieldLeak")
    private inner class UpdateEntitiesAsyncTask(val dao: TDao) : AsyncTask<List<TEntity>, Unit, Unit>() {
        override fun doInBackground(vararg entities: List<TEntity>?) {
            dao.update(entities[0]!!)
        }
    }

    @SuppressLint("StaticFieldLeak")
    private inner class InsertEntityAsyncTask(val dao: TDao) : AsyncTask<TEntity, Unit, Unit>() {
        override fun doInBackground(vararg entities: TEntity?) {
            dao.insert(entities[0]!!)
        }
    }

    @SuppressLint("StaticFieldLeak")
    private inner class UpdateEntityAsyncTask(val dao: TDao) : AsyncTask<TEntity, Unit, Unit>() {
        override fun doInBackground(vararg entities: TEntity?) {
            dao.update(entities[0]!!)
        }
    }

    @SuppressLint("StaticFieldLeak")
    private inner class DeleteEntityAsyncTask(val dao: TDao) : AsyncTask<TEntity, Unit, Unit>() {
        override fun doInBackground(vararg entities: TEntity?) {
            dao.delete(entities[0]!!)
        }
    }
}