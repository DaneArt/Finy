package com.rdd.finy.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity
class Wallet( title: String,
             budjet:Int, colorId: Int) {

    @PrimaryKey
    var id: Long = 0
}