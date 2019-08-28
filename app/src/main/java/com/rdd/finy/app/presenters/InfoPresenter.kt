package com.rdd.finy.app.presenters

import com.rdd.finy.app.views.InfoView
import com.rdd.finy.data.repositories.WalletRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class InfoPresenter(private val walletRepositoryImpl: WalletRepositoryImpl) : MvpPresenter<InfoView>() {

    private lateinit var walletsDisposable: Disposable
    fun loadWalletsData() {
        walletsDisposable = walletRepositoryImpl.getAllWalletsBalances()
            .observeOn(AndroidSchedulers.mainThread())
                .subscribe { balances ->
                    var totalBalance: Long = 0
                    for (balance in balances) {
                        totalBalance += balance
                    }
                    viewState.updateTotalBalanceState(totalBalance)
                }
    }

    fun destroyDisposable() {
        walletsDisposable.dispose()
    }

}