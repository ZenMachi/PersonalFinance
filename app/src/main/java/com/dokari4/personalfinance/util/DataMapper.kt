package com.dokari4.personalfinance.util

import com.dokari4.personalfinance.data.local.entity.AccountEntity
import com.dokari4.personalfinance.domain.model.Account

object DataMapper {
    fun mapAccountEntitiesToDomain(input: List<AccountEntity>): List<Account> =
        input.map { entity ->
            Account(
                id = entity.id,
                userId = entity.userId,
                accountType = entity.accountType,
                name = entity.name,
                amount = entity.amount
            )
        }

    fun mapDomainToEntity(input: Account) = AccountEntity(
        id = input.id,
        userId = input.userId,
        accountType = input.accountType,
        name = input.name,
        amount = input.amount
    )
}