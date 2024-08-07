package com.dokari4.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    var id: Int? = null,
    var name: String,
) : Parcelable
