package com.rdd.finy.app.presenters


import android.util.Log
import com.rdd.finy.app.models.Wallet
import com.rdd.finy.app.views.WalletsContainerView
import com.rdd.finy.data.repositories.WalletRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.*

@InjectViewState
class WalletsContainerPresenter(private val walletRepository: WalletRepository) : MvpPresenter<WalletsContainerView>() {

    private val TAG = WalletsContainerPresenter::class.java.simpleName

    private var localIdsList: List<Long> = listOf()
    private var localWalletsList: List<Wallet> = listOf()

    fun loadWalletsFromDB() {

        viewState.attachNewDisposable(
            walletRepository.getAllWallets()
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
                                    if (!walletsFromDB.contains(wallet)) {
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