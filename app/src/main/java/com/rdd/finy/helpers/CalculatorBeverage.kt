/*
Copyright 2019 Daniil Artamonov

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
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