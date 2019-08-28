package com.rdd.finy.helpers

import com.rdd.finy.app.models.Wallet

abstract class CalculatorBeverage {

    private val TAG = CalculatorBeverage::class.java.simpleName

    abstract var userBalance: Long
    abstract var userConfigWallets: HashMap<Wallet, Long>
    abstract var otherWallets: List<Wallet>

    abstract fun getCalculatedResult(): List<Wallet>
    abstract fun calculateUserConfigWallets()
    abstract fun calculateOtherWallets()
    abstract fun setupExtraWallet()

}