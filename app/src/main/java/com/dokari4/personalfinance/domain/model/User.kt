package com.dokari4.personalfinance.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var id: Int? = null,
    var name: String,
) : Parcelable
