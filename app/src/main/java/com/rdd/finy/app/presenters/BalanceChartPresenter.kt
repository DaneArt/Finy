package com.rdd.finy.app.presenters

import android.content.Context
import com.rdd.finy.App
import com.rdd.finy.app.views.BalanceChartView
import com.rdd.finy.data.repositories.WalletRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import lecho.lib.hellocharts.model.PieChartData
import lecho.lib.hellocharts.model.SliceValue
import lecho.lib.hellocharts.util.ChartUtils
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class BalanceChartPresenter(private val context: Context) : MvpPresenter<BalanceChartView>() {

    private val TAG = BalanceChartPresenter::class.java.simpleName

    init {
        App.app()?.appComponent()?.inject(this)
    }

    @Inject
    lateinit var walletsRepository: WalletRepository

    private lateinit var disposable: Disposable

    fun loadChartData() {
        disposable = walletsRepository.getActiveWalletsBalances()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ balances ->
                    updateStatsDiagramState(balances = balances)
                }, {
                    viewState.showError(it.localizedMessage)
                })
    }

    private fun updateStatsDiagramState(balances: DoubleArray) {

        val statsChartData: PieChartData

        val values = ArrayList<SliceValue>()

        for (balance in balances.filter { it > 0.0 }) {
            val sliceValue = SliceValue(balance.toFloat(), ChartUtils.pickColor())
            values.add(sliceValue)
        }

        statsChartData = PieChartData(values)
        statsChartData.setHasLabels(true)
        statsChartData.setHasLabelsOnlyForSelected(true)
        statsChartData.setHasLabelsOutside(false)
        statsChartData.setHasCenterCircle(false)

        if (values.isEmpty()) {
            viewState.showNoActiveWalletsWithPositiveBalance()
        } else {
            viewState.updatePieData(statsChartData)
        }

    }

    fun dispose() {
        disposable.dispose()
    }

}