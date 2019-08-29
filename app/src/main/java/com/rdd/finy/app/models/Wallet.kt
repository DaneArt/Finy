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
package com.rdd.finy.app.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rdd.finy.helpers.Colors

@Entity
class Wallet(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var title: String = "",
    var isActive: Boolean = true,
    //var currency: String = Currency.getInstance(Locale.US).displayName,
    var backColor: String = Colors.BLUE.hex
) {

    constructor(
        balance: Long,
        upperDivider: Long, bottomDivider: Long
    ) : this() {
        this.balance = balance
        this.bottomDivider = bottomDivider
        this.upperDivider = upperDivider
    }

    var balance: Long = 0
        set(value) {
            field = when {
                upperDivider != null && value > upperDivider!! -> throw Exception("Upper divider should be more or equals balance")
                bottomDivider != null && value < bottomDivider!! -> throw Exception("Bottom divider should be less or equals balance")
                else -> value
            }
        }
    var upperDivider: Long? = null
        set(value) {
            value?.let { arg ->
                if (arg < balance)
                    throw Exception("Upper divider should be more than bottom and balance")
                else if (bottomDivider != null && arg < bottomDivider!!)
                    throw Exception("Upper divider should be more than bottom and balance")
                else field = arg
            }

        }
    var bottomDivider: Long? = null
        set(value) {
            if (value != null) {
                if (value > balance)
                    throw Exception("Bottom divider should be less than upper and balance")
                else if (upperDivider != null && value > upperDivider!!)
                    throw Exception("Bottom divider should be less than upper and balance")
                else field = value
            }

        }

//    override fun equals(other: Any?): Boolean {
//
//        val wallet: Wallet = other as Wallet
//
//        return wallet.id == this.id &&
//                wallet.balance == this.balance &&
//                wallet.title == this.title &&
////                wallet.currency == this.currency &&
////                wallet.backColor == this.backColor &&
//                wallet.upperDivider == this.upperDivider &&
//                wallet.bottomDivider == this.bottomDivider
//    }

}