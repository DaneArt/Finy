package com.rdd.finy.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(value =  AddToEndSingleStrategy::class)
interface WalletView : MvpView {


    fun updateViewState(name : String, budjet : Int, colorId: Int)

    fun startEditingWallet()

    fun endEditingWallet()

}