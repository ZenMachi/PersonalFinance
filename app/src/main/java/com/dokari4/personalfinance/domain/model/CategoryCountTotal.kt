package com.dokari4.personalfinance.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryCountTotal(
    val id: Int,
    val name: String,
    val count: Int
) : Parcelable