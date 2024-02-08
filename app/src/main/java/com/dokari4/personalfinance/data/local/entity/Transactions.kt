package com.dokari4.personalfinance.data.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Transactions(
    var id: Int,
    var accountId: Int,
    var categoryId: Int,
    var name: String,
    var description: String,
    var dateTime: String,
    var type: String,
    var amount: Double,
) : Parcelable
