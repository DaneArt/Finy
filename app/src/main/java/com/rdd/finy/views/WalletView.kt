package com.rdd.finy.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.rdd.finy.data.Wallet

@StateStrategyType(value =  AddToEndSingleStrategy::class)
interface WalletView : MvpView {


    fun setupViews(name : String, count : Int, colorId: Int)


}