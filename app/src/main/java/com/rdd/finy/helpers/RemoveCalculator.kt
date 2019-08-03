package com.rdd.finy.helpers

import com.rdd.finy.app.models.Wallet

class RemoveCalculator(
    override var userBalance: Int,
    override var userConfigWallets: HashMap<Wallet, Int>,
    override var otherWallets: List<Wallet>
) : CalculatorBeverage() {
    override fun getCalculatedResult(): List<Wallet> {
        throw UnsupportedOperationException()
    }

    override fun calculateUserConfigWallets() {
        throw UnsupportedOperationException()
    }

    override fun calculateOtherWallets() {
        throw UnsupportedOperationException()
    }

}