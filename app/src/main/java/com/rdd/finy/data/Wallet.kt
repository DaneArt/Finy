package com.rdd.finy.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

@Entity
class Wallet( @PrimaryKey(autoGenerate = true)
              var id: Long = 0,
              var title: String = "",
              var budjet: Int = 0,
              var colorId: Int = android.R.color.holo_green_light)
