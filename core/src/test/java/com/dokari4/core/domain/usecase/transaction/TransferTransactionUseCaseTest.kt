package com.dokari4.core.domain.usecase.transaction

import com.dokari4.core.domain.model.Transaction
import com.dokari4.core.domain.repository.FakeAppRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class TransferTransactionUseCaseTest {

    private lateinit var transferTransactionUseCase: TransferTransactionUseCase
    private lateinit var repository: FakeAppRepository

    val dummyTransaction = Transaction(
        id = 1,
        accountId = 7178,
        categoryId = 1959,
        name = "Example Transaction",
        description = "lorem",
        dateTime = 9778,
        type = "Expense",
        amount = 42.43
    )
    val dummyTransaction2 = dummyTransaction.copy(id = 2, accountId = 7179, categoryId = 1960, type = "Income")

    @Before
    fun setUp() {
        repository = FakeAppRepository()
        transferTransactionUseCase = TransferTransactionUseCase(repository)
    }

    @Test
    fun `transfer transaction, contains two transactions`() = runBlocking {
        transferTransactionUseCase(dummyTransaction, dummyTransaction2)

        repository.getTransactionList().collect {
            Truth.assertThat(it.size).isEqualTo(2)
        }
    }
}