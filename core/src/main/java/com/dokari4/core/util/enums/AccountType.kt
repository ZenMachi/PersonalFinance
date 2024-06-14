package com.dokari4.core.util.enums

enum class AccountType(val description: String) {
    CASH("Cash"),
    BANK("Bank"),
    E_WALLET("E-Wallet");

    override fun toString(): String {
        return description
    }

    companion object {
        fun fromDescription(description: String): AccountType {
            return entries.first { it.description == description }
        }
    }
}