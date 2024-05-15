package com.dokari4.personalfinance.data.local.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryCountTotal(
    val id: Int,
    val name: String,
    @ColumnInfo(name = "total_transaction")
    val count: Int,
    val amount: Double,
) : Parcelable