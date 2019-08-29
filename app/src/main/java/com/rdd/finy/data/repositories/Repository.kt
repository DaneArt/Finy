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