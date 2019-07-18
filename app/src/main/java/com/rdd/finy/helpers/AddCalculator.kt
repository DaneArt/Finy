package com.rdd.finy.helpers

import android.util.Log
import com.rdd.finy.data.Wallet
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.*

class AddCalculator(override var activeWallets: HashMap<Wallet, Double>) : CalculatorBehaviour() {

    private val TAG = AddCalculator::class.java.simpleName

    private lateinit var disposable: Disposable
    override fun calculate() {
        disposable = walletsRepository.getAllWallets()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { wallets ->
                autoCalcWallets.addAll(
                    wallets.filter { wallet ->
                        !activeWallets.map {
                            it.key.id
                        }.contains(wallet.id)
                    })
                calcActive()
                calcOthers()
                sendResultToDB()
                disposable.dispose()
            }
    }

    override fun calcActive() {
        for (wallet in activeWallets) {
            if (wallet.value + summaryBalance < userBalance
                && wallet.key.couldBeCalculated(wallet.value)
            ) {
                wallet.key.balance += wallet.value
                summaryBalance += wallet.value
                Log.e(TAG, "Add money to active. Summary: $summaryBalance. Value: ${wallet.value}")
            } else {
                autoCalcWallets.add(wallet.key)
                activeWallets.remove(wallet.key)
            }
        }

    }

    override fun calcOthers() {
        var othersBalance = userBalance - summaryBalance
        var othersCount = autoCalcWallets.size

        autoCalcWallets.sortBy { it.upperDivider - it.balance }

        val othersWithDiv = autoCalcWallets.filter { it.hasUpperDivider }
        val othersWithoutDiv = autoCalcWallets.filter { !it.hasUpperDivider }

        Log.e(
            TAG,
            "Sorted array: ${Arrays.toString(autoCalcWallets.map { it.upperDivider - it.balance }.toDoubleArray())}"
        )

        for (wallet in othersWithDiv) {
            val value: Double = othersBalance / othersCount
            if (wallet.couldBeCalculated(value)) {
                wallet.balance += value
            } else {
                othersBalance -= wallet.upperDivider - wallet.balance
                wallet.balance = wallet.upperDivider
                othersCount--
            }
        }

        if (othersWithoutDiv.isNotEmpty()) {
            val value: Double = othersBalance / othersCount

            for (wallet in othersWithoutDiv) wallet.balance += value
        } else if (othersBalance > 0) {
            val extraWallet = Wallet()
            extraWallet.title = "Extra"
            extraWallet.balance = othersBalance

            walletsRepository.insertWallet(extraWallet)
        }
        autoCalcWallets.addAll(othersWithDiv)
        autoCalcWallets.addAll(othersWithoutDiv)


    }


}