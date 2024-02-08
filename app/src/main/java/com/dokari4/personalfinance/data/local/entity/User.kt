package com.dokari4.personalfinance.data.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var id: Int,
    var name: String,
) : Parcelable
