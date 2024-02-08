package com.dokari4.personalfinance.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Account")
data class AccountEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "user_id")
    var userId: Int,

    @ColumnInfo(name = "account_type")
    var accountType: String,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "amount")
    var amount: Double,
) : Parcelable
