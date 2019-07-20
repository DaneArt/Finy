package com.rdd.finy.app.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Wallet(@PrimaryKey(autoGenerate = true)
              var id: Long = 0,
             var title: String = "",
             //var currency: String = Currency.getInstance(Locale.US).displayName,
             var balance: Double = 0.0,
             //var backColor: Int = Color.MAGENTA,
             var bottomDivider: Double = 0.0,
             var upperDivider: Double = -1.0,
             var isActive: Boolean = true,
             var isBalanceShown: Boolean = false)
{

    val hasUpperDivider : Boolean
    get() {
        return upperDivider >= 0.0
    }

    val hasBottomDivider: Boolean
    get(){
        return bottomDivider > 0.0
    }

    val couldBeSaved: Boolean
    get(){
        if(upperDivider<balance && hasUpperDivider)return false
        if(bottomDivider>balance && hasBottomDivider)return false
        return true
    }

    fun couldBeCalculated(value : Double): Boolean{
        if(hasUpperDivider && (balance+value)>upperDivider) return false
        if((balance+value)<bottomDivider) return false
        return true
    }


    override fun equals(other: Any?): Boolean {

        val wallet: Wallet = other as Wallet

        return wallet.id == this.id &&
                wallet.balance == this.balance &&
                wallet.title == this.title &&
//                wallet.currency == this.currency &&
//                wallet.backColor == this.backColor &&
                wallet.upperDivider == this.upperDivider &&
                wallet.bottomDivider == this.bottomDivider &&
                wallet.isActive == this.isActive &&
                wallet.isBalanceShown == this.isBalanceShown
    }
}