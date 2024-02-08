package com.dokari4.personalfinance.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    var id: Int,
    var name: String,
) : Parcelable
