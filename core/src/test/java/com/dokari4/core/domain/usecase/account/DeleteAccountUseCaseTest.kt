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

class DeleteAccountUseCaseTest {

    private lateinit var repository: FakeAppRepository
    private lateinit var deleteAccountUseCase: DeleteAccountUseCase

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
        deleteAccountUseCase = DeleteAccountUseCase(repository)

        runBlocking {
            repository.insertAccount(accountDummy)
        }
    }

    @Test
    fun `delete account, account deleted`() = runBlocking {
        deleteAccountUseCase(accountDummy)
        repository.getAccountList().collect {
            Truth.assertThat(it).isEmpty()
        }
    }
}