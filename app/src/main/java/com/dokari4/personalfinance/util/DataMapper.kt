package com.dokari4.personalfinance.util

import com.dokari4.personalfinance.data.local.entity.AccountEntity
import com.dokari4.personalfinance.data.local.entity.CategoryEntity
import com.dokari4.personalfinance.data.local.entity.TransactionEntity
import com.dokari4.personalfinance.data.local.entity.UserEntity
import com.dokari4.personalfinance.data.local.model.AccountWithTransactions
import com.dokari4.personalfinance.domain.model.Account
import com.dokari4.personalfinance.domain.model.Category
import com.dokari4.personalfinance.domain.model.Transaction
import com.dokari4.personalfinance.domain.model.User

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

    fun mapDomainToAccountEntity(input: Account) = AccountEntity(
        id = input.id,
        userId = input.userId,
        accountType = input.accountType,
        name = input.name,
        amount = input.amount
    )

    fun mapUserEntityToDomain(input: UserEntity) = User(id = input.id, name = input.name)

    fun mapDomainToUserEntity(input: User) = UserEntity(id = input.id, name = input.name)

    fun mapCategoryEntityToDomain(input: List<CategoryEntity>): List<Category> =
        input.map { entity ->
            Category(id = entity.id, name = entity.name)
        }

    fun mapDomainToCategoryEntity(input: Category) =
        CategoryEntity(id = input.id, name = input.name)

    fun mapTransactionEntityToDomain(input: List<TransactionEntity>): List<Transaction> =
        input.map { entity ->
            Transaction(
                id = entity.id,
                accountId = entity.accountId,
                categoryId = entity.categoryId,
                name = entity.name,
                description = entity.description,
                dateTime = entity.dateTime,
                type = entity.type,
                amount = entity.amount
            )
        }

    fun mapDomainToTransactionEntity(input: Transaction) =
        TransactionEntity(
            id = input.id,
            accountId = input.accountId,
            categoryId = input.categoryId,
            name = input.name,
            description = input.description,
            dateTime = input.dateTime,
            type = input.type,
            amount = input.amount
        )

    fun mapAccountWithTransactionsEntityToDomain(input: List<AccountWithTransactions>):
            List<com.dokari4.personalfinance.domain.model.AccountWithTransactions> =
        input.map { entity ->
            com.dokari4.personalfinance.domain.model.AccountWithTransactions(
                id = entity.id,
                name = entity.name,
                accountType = entity.accountType,
                amount = entity.amount,
                totalIncome = entity.totalIncome,
                totalExpense = entity.totalExpense
            )
        }
}