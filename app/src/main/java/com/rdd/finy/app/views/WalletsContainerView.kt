package com.rdd.finy.app.views

import com.rdd.finy.app.models.Wallet
import io.reactivex.disposables.Disposable
import moxy.MvpView
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = SingleStateStrategy::class)
interface WalletsContainerView : MvpView {

    fun setupEmptyWalletsList()

    fun setupWalletsList(wallets : List<Wallet>)

    fun updateCurrentWallet(wallet: Wallet)

    fun removeCurrentWallet(wallet: Wallet)

    fun insertCurrentWallet(wallet: Wallet)

    fun showError(message: String)

    fun attachNewDisposable(d: Disposable)

}