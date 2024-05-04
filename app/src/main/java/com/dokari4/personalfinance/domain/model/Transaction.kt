package com.dokari4.personalfinance.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Transaction(
    var id: Int? = null,
    var accountId: Int,
    var categoryId: Int,
    var name: String,
    var description: String,
    var dateTime: Long,
    var type: String,
    var amount: Double,
) : Parcelable
