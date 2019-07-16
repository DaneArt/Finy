package com.rdd.finy.views

import com.rdd.finy.data.Wallet
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

}