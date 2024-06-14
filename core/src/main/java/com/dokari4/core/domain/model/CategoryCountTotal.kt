package com.dokari4.core.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryCountTotal(
    val id: Int,
    val name: String,
    val count: Int,
    val amount: Double
) : Parcelable