package com.rdd.finy.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.graphics.Color

@Entity
class Wallet(@PrimaryKey(autoGenerate = true)
              var id: Long = 0,
             var name: String = "",
             var budjet: Int = 0,
             var backColorId: Int = Color.CYAN)
