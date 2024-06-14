package com.dokari4.core.domain.usecase.transaction

import com.dokari4.core.domain.model.Transaction
import com.dokari4.core.domain.repository.FakeAppRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class AddTransactionUseCaseTest {

    private lateinit var addTransactionUseCase: AddTransactionUseCase
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
        addTransactionUseCase = AddTransactionUseCase(repository)
    }

    @Test
    fun `add transaction, contain dummyTransaction`() = runBlocking {
        addTransactionUseCase(dummyTransaction)

        repository.getTransactionList().collect {
            Truth.assertThat(it).contains(dummyTransaction)
        }
    }
}