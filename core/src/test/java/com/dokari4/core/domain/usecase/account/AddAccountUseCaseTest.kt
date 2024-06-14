package com.dokari4.core.domain.usecase.account

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.dokari4.core.data.local.room.AppDao
import com.dokari4.core.data.local.room.AppDatabase
import com.dokari4.core.domain.model.Account
import com.dokari4.core.domain.repository.FakeAppRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddAccountUseCaseTest {

    private lateinit var repository: FakeAppRepository
    private lateinit var addAccountUseCase: AddAccountUseCase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        repository = FakeAppRepository()
        addAccountUseCase = AddAccountUseCase(repository)
    }

    @Test
    fun `insert account, correct account added`() = runBlocking {
        val accountDummy = Account(
            id = 1,
            userId = 9821,
            accountType = "eos",
            name = "Bernadette Wilson",
            amount = 2.3
        )

        val falseAccount = Account(
            id = 1,
            userId = 4294,
            accountType = "tation",
            name = "Nina Baird",
            amount = 6.7
        )

        addAccountUseCase(accountDummy)

        repository.getAccountList().collect {
            Truth.assertThat(it).contains(accountDummy)
        }
//        Truth.assertThat(accountDummy).isEqualTo(repository.getAccountList())
    }
}