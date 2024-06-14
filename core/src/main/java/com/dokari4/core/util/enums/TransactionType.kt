package com.dokari4.core.util.enums

enum class TransactionType(val description: String) {
    INCOME("Income"),
    EXPENSE("Expense");

    override fun toString(): String {
        return description
    }

    companion object {
        fun fromDescription(description: String): TransactionType {
            return entries.first { it.description == description }
        }
    }
}