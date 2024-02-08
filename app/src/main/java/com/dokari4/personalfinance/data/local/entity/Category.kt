package com.dokari4.personalfinance.data.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    var id: Int,
    var name: String,
) : Parcelable
