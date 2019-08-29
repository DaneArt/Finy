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

import android.util.Log
import com.rdd.finy.app.models.Wallet
import com.rdd.finy.app.views.WalletsContainerView
import com.rdd.finy.data.repositories.WalletRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.*

@InjectViewState
class WalletsContainerPresenter(private val walletRepositoryImpl: WalletRepositoryImpl) :
    MvpPresenter<WalletsContainerView>() {

    private val TAG = WalletsContainerPresenter::class.java.simpleName

    private var localIdsList: List<Long> = listOf()
    private var localWalletsList: List<Wallet> = listOf()

    fun loadWalletsFromDB() {

        viewState.attachNewDisposable(
            walletRepositoryImpl.getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { walletsFromDB ->
                        Log.e(TAG, Arrays.toString(localIdsList.toLongArray()))
                        when {
                            localIdsList.isEmpty() -> {
                                viewState.setupWalletsList(walletsFromDB)
                            }
                            walletsFromDB.size > localIdsList.size -> {
                                viewState.insertCurrentWallet(walletsFromDB[walletsFromDB.size - 1])
                            }
                            walletsFromDB.size < localIdsList.size -> {
                                for (wallet in localWalletsList) {
                                    if (!walletsFromDB.map { it.id }.contains(wallet.id)) {
                                        viewState.removeCurrentWallet(wallet)
                                    }
                                }
                            }
                            walletsFromDB.size == localIdsList.size -> {
                                for (wallet in localWalletsList) {
                                    if (!walletsFromDB.contains(wallet)) {
                                        val index = localWalletsList.indexOf(wallet)
                                        viewState.updateCurrentWallet(walletsFromDB[index])
                                    }
                                }
                            }
                        }
                        localIdsList = walletsFromDB.map { it.id }
                        localWalletsList = walletsFromDB
                    },
                    { error ->
                        viewState.showError(error.localizedMessage)
                    }
                )
        )
    }

}