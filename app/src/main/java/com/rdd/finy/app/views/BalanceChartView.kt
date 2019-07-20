package com.rdd.finy.app.views

import lecho.lib.hellocharts.model.PieChartData
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface BalanceChartView : MvpView {

    fun updatePieData(data: PieChartData)

    fun showError(message: String)

    fun showNoActiveWalletsWithPositiveBalance()

}