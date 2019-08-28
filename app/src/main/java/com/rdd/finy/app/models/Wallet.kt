package com.rdd.finy.app.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Wallet(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var title: String = "",
    var isActive: Boolean = true
    //var currency: String = Currency.getInstance(Locale.US).displayName,
    //var backColor: Int = Color.MAGENTA,
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