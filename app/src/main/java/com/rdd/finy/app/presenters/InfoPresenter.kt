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
    fun loadWalletsData(){
        walletsDisposable = walletRepository.getAllWallets()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {wallets ->
                var totalBalance = 0.0
                for (wallet in wallets){
                    totalBalance +=wallet.balance
                }
                viewState.updateTotalBalanceState(totalBalance)
            }
    }

    fun destroyDisposable(){
        walletsDisposable.dispose()
    }

}