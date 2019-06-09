package com.rdd.finy.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity
class Wallet(@PrimaryKey var id:Long,title: String,
             budjet:Int, colorId: Int) {
}