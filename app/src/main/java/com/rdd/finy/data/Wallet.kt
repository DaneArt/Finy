package com.rdd.finy.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class Wallet( @PrimaryKey(autoGenerate = true)
              var id: Int,
              var title: String?,
              var budjet: Int?,
              var colorId: Int?)
