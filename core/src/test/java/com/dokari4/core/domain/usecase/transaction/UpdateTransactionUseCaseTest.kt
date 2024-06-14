package com.dokari4.core.domain.usecase.transaction

import com.dokari4.core.domain.model.Transaction
import com.dokari4.core.domain.repository.FakeAppRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class UpdateTransactionUseCaseTest {

    private lateinit var updateTransactionUseCase: UpdateTransactionUseCase
    private lateinit var repository: FakeAppRepository

    val dummyTransaction = Transaction(
        id = 1,
        accountId = 7178,
        categoryId = 1959,
        name = "Joseph Levy",
        description = "lorem",
        dateTime = 9778,
        type = "dolore",
        amount = 42.43
    )

    @Before
    fun setUp() {
        repository = FakeAppRepository()
        updateTransactionUseCase = UpdateTransactionUseCase(repository)

        runBlocking {
            repository.insertTransaction(dummyTransaction)
        }
    }

    @Test
    fun `update transaction, Transaction should be updated`() = runBlocking {
        val updatedTransaction = dummyTransaction.copy(type = "income")

        updateTransactionUseCase(updatedTransaction)

        repository.getTransactionList().collect {
            val result = it.find { transaction -> transaction.id == dummyTransaction.id }

            Truth.assertThat(result?.type).isEqualTo(updatedTransaction.type)
        }
    }
}