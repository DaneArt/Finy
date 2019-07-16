package com.rdd.finy.presenters

import com.rdd.finy.App
import com.rdd.finy.data.WalletRepository
import com.rdd.finy.views.ControlMoneyView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class ControlMoneyPresenter : MvpPresenter<ControlMoneyView>() {

    @Inject
    lateinit var walletRepository: WalletRepository

    init {
        App.app()?.appComponent()?.inject(this)
    }

    private lateinit var walletsDisposable: Disposable

    fun loadWalletsFromDB() {

        walletsDisposable = walletRepository.getAllWallets()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.setupSourceList(it)
                    walletsDisposable.dispose()
                }, {

                })

    }
}