package com.dokari4.core.domain.usecase.register

import com.dokari4.core.domain.model.User
import com.dokari4.core.domain.repository.FakeAppRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class AddUserUseCaseTest {

    private lateinit var repository: FakeAppRepository
    private lateinit var addUserUseCase: AddUserUseCase

    @Before
    fun setUp() {
        repository = FakeAppRepository()
        addUserUseCase = AddUserUseCase(repository)
    }

    @Test
    fun `add User, should contain same user`() = runBlocking {
        addUserUseCase(User(name = "dokari"))

        repository.getUserName().collect {
            Truth.assertThat(it).isEqualTo("dokari")
        }
    }
}