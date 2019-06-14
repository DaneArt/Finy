package com.rdd.finy.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.rdd.finy.data.Wallet

@StateStrategyType(value =  AddToEndSingleStrategy::class)
interface InfoView : MvpView {

    fun setupEmptyWalletsList()

    fun setupWalletsList(wallets : List<Wallet>)
}