package com.rdd.finy.app.presenters

import android.content.Context
import com.rdd.finy.App
import com.rdd.finy.app.views.BalanceChartView
import com.rdd.finy.data.repositories.WalletRepositoryImpl
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
    lateinit var walletsRepositoryImpl: WalletRepositoryImpl

    private lateinit var disposable: Disposable

    fun loadChartData() {
        disposable = walletsRepositoryImpl.getActiveWalletsBalances()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ balances ->
                    updateStatsDiagramState(balances = balances)
                }, {
                    viewState.showError(it.localizedMessage)
                })
    }

    private fun updateStatsDiagramState(balances: IntArray) {

        val statsChartData: PieChartData

        val values = ArrayList<SliceValue>()

        for (balance in balances.filter { it > 0 }) {


            val sliceValue = SliceValue(
                (balance / 100) + (balance % 100 / 100f),
                ChartUtils.pickColor()
            )
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