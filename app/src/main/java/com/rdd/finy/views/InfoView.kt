package com.rdd.finy.views


import com.rdd.finy.data.Wallet
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface InfoView : MvpView {

    fun showControlMoneyDialog(isAddingMoney : Boolean)

    fun updateTotalBalanceState(totalBalance : Double)

    fun updateStatsDiagramState(walletsData: List<Wallet>)

    fun expandFAB()

    fun hideFAB()

}