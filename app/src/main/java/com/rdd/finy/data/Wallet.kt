package com.rdd.finy.data

import android.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
class Wallet(@PrimaryKey(autoGenerate = true)
              var id: Long = 0,
             var title: String = "",
             var currency: String = Currency.getInstance(Locale.US).displayName,
             var balance: Double = 0.0,
             var backColor: Int = Color.MAGENTA,
             var bottomDivider: Double = 0.0,
             var upperDivider: Double = -1.0,
             var isActive: Boolean = true)
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
}