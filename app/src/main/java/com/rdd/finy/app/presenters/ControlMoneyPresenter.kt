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
package com.rdd.finy.app.presenters

import com.rdd.finy.App
import com.rdd.finy.app.views.ControlMoneyView
import com.rdd.finy.data.repositories.WalletRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class ControlMoneyPresenter : MvpPresenter<ControlMoneyView>() {

    @Inject
    lateinit var walletRepositoryImpl: WalletRepositoryImpl

    init {
        App.app()?.appComponent()?.inject(this)
    }

    private val disposables: CompositeDisposable = CompositeDisposable()

    fun loadWalletsFromDB() {

        disposables.add(
            walletRepositoryImpl.getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.setupSourceList(it)
                }, {

                })
        )
    }

    fun attachDisposable(d: Disposable) {
        disposables.add(d)
    }

    fun destroyDisposable() {
        disposables.clear()
    }
}