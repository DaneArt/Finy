package com.rdd.finy.presenters

import com.rdd.finy.data.Wallet
import com.rdd.finy.data.WalletRepository
import com.rdd.finy.views.ControlWalletsView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class ControlWalletsPresenter(val walletRepository: WalletRepository) : MvpPresenter<ControlWalletsView>() {

    private lateinit var localWalletsList: List<Wallet>
    private lateinit var walletsDisposable: Disposable

    fun loadWalletsFromDB() {

        walletsDisposable = walletRepository.getAllWallets()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {walletsFromDB ->
                            localWalletsList = walletsFromDB
                            updateViewState()

                        },
                        {error ->

                        }
                )
    }

    private fun updateViewState() {

    }

    fun disposeWalletsList() {
        walletsDisposable.dispose()
    }

}