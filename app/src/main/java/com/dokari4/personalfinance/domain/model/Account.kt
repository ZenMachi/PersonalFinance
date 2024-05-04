package com.dokari4.personalfinance.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Account(
    var id: Int? = null,
    var userId: Int,
    var accountType: String,
    var name: String,
    var amount: Double,
) : Parcelable
