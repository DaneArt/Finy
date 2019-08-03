package com.rdd.finy.app.presenters

import com.rdd.finy.app.views.InfoView
import com.rdd.finy.data.repositories.WalletRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class InfoPresenter(private val walletRepository: WalletRepository) : MvpPresenter<InfoView>() {

    fun removeMoney() {
        viewState.showControlMoneyDialog(isAddingMoney = false)
    }

    fun addMoney() {
        viewState.showControlMoneyDialog(isAddingMoney = true)
    }

    private lateinit var walletsDisposable: Disposable
    fun loadWalletsData() {
        walletsDisposable = walletRepository.getAllWalletsBalances()
            .observeOn(AndroidSchedulers.mainThread())
                .subscribe { balances ->
                    var totalBalance = 0
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