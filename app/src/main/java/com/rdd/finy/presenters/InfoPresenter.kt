package com.rdd.finy.presenters

import com.rdd.finy.data.WalletRepository
import com.rdd.finy.views.InfoView
import io.reactivex.android.schedulers.AndroidSchedulers
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class InfoPresenter(val walletRepository: WalletRepository) : MvpPresenter<InfoView>() {

    fun removeMoney() {
        viewState.showControlMoneyDialog(isAddingMoney = false)
    }

    fun addMoney() {
        viewState.showControlMoneyDialog(isAddingMoney = true)
    }

    fun loadWalletsData(){
        walletRepository.getAllWallets()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                var totalBalance = 0.0
                for (wallet in it){
                    totalBalance +=wallet.balance
                }
                viewState.updateTotalBalanceState(totalBalance)

                viewState.updateStatsDiagramState(it)
            }
    }

}