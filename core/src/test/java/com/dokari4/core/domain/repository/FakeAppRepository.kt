package com.dokari4.core.domain.repository

import com.dokari4.core.data.local.room.AppDao
import com.dokari4.core.domain.model.Account
import com.dokari4.core.domain.model.AccountWithTransactions
import com.dokari4.core.domain.model.Category
import com.dokari4.core.domain.model.CategoryCountTotal
import com.dokari4.core.domain.model.Transaction
import com.dokari4.core.domain.model.User
import com.dokari4.core.util.enums.OnboardingState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeAppRepository(): AppRepository {

    private val accounts = mutableListOf<Account>()
    private val transactions = mutableListOf<Transaction>()
    private val categories = mutableListOf<Category>()
    private val users = mutableListOf<User>()
    private val accountWithTransactions = mutableListOf<AccountWithTransactions>()
    private val categoryCountTotal = mutableListOf<CategoryCountTotal>()
    private var onboardingState = OnboardingState.NOT_DONE


    override fun getAccountList(): Flow<List<Account>> {
        return flow { emit(accounts) }
    }

    override suspend fun insertAccount(account: Account) {
        accounts.add(account)
    }

    override suspend fun updateAccount(account: Account) {
        val indexToUpdate = accounts.indexOfFirst { it.id == account.id }

        if (indexToUpdate != -1) {
            val updatedAccount = accounts[indexToUpdate].copy(
                name = account.name,
                accountType = account.accountType,
                amount = account.amount
            )
            accounts[indexToUpdate] = updatedAccount
        }

        //Another Step Maybe

       /* accounts.find { it.id == account.id }?.let {
            it.userId = account.userId
            it.name = account.name
            it.accountType = account.accountType
            it.amount = account.amount
        }*/
    }

    override suspend fun deleteAccount(account: Account) {
        val indexToDelete = accounts.indexOfFirst { it.id == account.id }
        if (indexToDelete != -1) {
            accounts.removeAt(indexToDelete)
        }

        //Another Step Maybe

        /*accounts.remove(account)*/
    }

    override suspend fun insertUser(user: User) {
        users.add(user)
    }

    override suspend fun insertTransaction(transaction: Transaction) {
        transactions.add(transaction)
    }

    override suspend fun transferTransaction(from: Transaction, to: Transaction) {
        val valueFrom = Transaction(
            id = 1,
            accountId = from.accountId,
            categoryId = from.categoryId,
            name = from.name,
            description = "Transfer from 1 to 2",
            dateTime = from.dateTime,
            type = "Expense",
            amount = from.amount
        )
        val valueTo = Transaction(
            id = 2,
            accountId = from.accountId,
            categoryId = from.categoryId,
            name = from.name,
            description = "Received from 1 to 2",
            dateTime = from.dateTime,
            type = "Income",
            amount = from.amount
        )
        transactions.add(valueFrom)
        transactions.add(valueTo)
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        val indexToUpdate = transactions.indexOfFirst { it.id == transaction.id }

        if (indexToUpdate != -1) {
            val updatedTransaction = transactions[indexToUpdate].copy(
                id = transaction.id,
                categoryId = transaction.categoryId,
                description = transaction.description,
                dateTime = transaction.dateTime,
                type = transaction.type,
                accountId = transaction.accountId,
                name = transaction.name,
                amount = transaction.amount
            )
            transactions[indexToUpdate] = updatedTransaction
        }
    }

    override suspend fun deleteTransaction(transaction: Transaction) {
        transactions.remove(transaction)
    }

    override suspend fun insertCategory(category: Category) {
        categories.add(category)
    }

    override fun getUserName(): Flow<String> {
        return flow { emit(users.first().name) }
    }

    override fun getTransactionList(): Flow<List<Transaction>> {
        return flow { emit(transactions) }
    }

    override fun getCategoryList(): Flow<List<Category>> {
        return flow { emit(categories) }
    }

    override fun getAccountExpenseList(accountId: Int): Flow<List<Transaction>> {
        TODO("Not yet implemented")
    }

    override fun getAccountIncomeList(accountId: Int): Flow<List<Transaction>> {
        TODO("Not yet implemented")
    }

    override fun getAccountsWithTransactions(): Flow<List<AccountWithTransactions>> {
        val accountWithTransactionsDummy = AccountWithTransactions(
            id = 1,
            name = "Ella Fleming",
            accountType = "lorem",
            amount = 18.19,
            totalIncome = 20.21,
            totalExpense = 22.23
        )
        accountWithTransactions.add(accountWithTransactionsDummy)

        return flow { emit(accountWithTransactions) }
    }

    override fun getCategoryTotalTransaction(type: String): Flow<List<CategoryCountTotal>> {

        when (type) {
            "income" -> {
                val dummyCategoryCountTotal = CategoryCountTotal(
                    id = 1,
                    name = "Income User",
                    count = 9580,
                    amount = 26.27
                )
                categoryCountTotal.add(dummyCategoryCountTotal)

                return flow { emit(categoryCountTotal) }
            }
            "expense" -> {
                val dummyCategoryCountTotal2 = CategoryCountTotal(
                    id = 2,
                    name = "Expense User",
                    count = 3061,
                    amount = 30.31
                )
                categoryCountTotal.add(dummyCategoryCountTotal2)

                return flow { emit(categoryCountTotal) }
            }
            else -> { return flow { emit(categoryCountTotal) } }
        }
    }

    override fun checkOnboardingState(): Flow<OnboardingState> {
        return flow { emit(onboardingState) }
    }

    override suspend fun setOnboardingState(state: OnboardingState) {
        onboardingState = state
    }
}