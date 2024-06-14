package com.dokari4.core.domain.usecase.account

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dokari4.core.domain.model.Account
import com.dokari4.core.domain.model.AccountWithTransactions
import com.dokari4.core.domain.repository.FakeAppRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ViewAccountWithTransactionUseCaseTest {

    private lateinit var repository: FakeAppRepository
    private lateinit var viewAccountWithTransactionUseCase: ViewAccountWithTransactionUseCase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = FakeAppRepository()
        viewAccountWithTransactionUseCase = ViewAccountWithTransactionUseCase(repository)
    }

    @Test
    fun `view account with transaction, should return correct data`(): Unit = runBlocking {
        viewAccountWithTransactionUseCase().collect {
            val result = it.first()
            val id = result.id

            Truth.assertThat(id).isEqualTo(1)
        }
    }
}