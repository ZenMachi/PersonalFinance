package com.dokari4.core.domain.usecase.account

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dokari4.core.domain.model.Account
import com.dokari4.core.domain.repository.FakeAppRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UpdateAccountUseCaseTest {

    private lateinit var repository: FakeAppRepository
    private lateinit var updateAccountUseCase: UpdateAccountUseCase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    val accountDummy = Account(
        id = null,
        userId = 8928,
        accountType = "ferri",
        name = "Gene Avery",
        amount = 10.11
    )

    @Before
    fun setUp() {
        repository = FakeAppRepository()
        updateAccountUseCase = UpdateAccountUseCase(repository)

        runBlocking {
            repository.insertAccount(accountDummy)
        }
    }

    @Test
    fun `update account, account updated`() = runBlocking {
        val updatedAccount = accountDummy.copy(name = "Updated Name")
        updateAccountUseCase(updatedAccount)

        repository.getAccountList().collect {
            val result = it.find { it.id == updatedAccount.id }

            Truth.assertThat(result?.name).isEqualTo(updatedAccount.name)
        }
    }
}