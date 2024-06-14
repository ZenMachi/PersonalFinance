package com.dokari4.core.domain.usecase.home

import com.dokari4.core.domain.model.User
import com.dokari4.core.domain.repository.FakeAppRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class ViewUsernameUseCaseTest {

    private lateinit var viewUsernameUseCase: ViewUsernameUseCase
    private lateinit var repository: FakeAppRepository

    @Before
    fun setUp() {
        repository = FakeAppRepository()
        viewUsernameUseCase = ViewUsernameUseCase(repository)

        runBlocking {
            repository.insertUser(User(name = "dokari4"))
        }
    }

    @Test
    fun `check username, return dokari4`() = runBlocking {
        viewUsernameUseCase().collect {
            Truth.assertThat(it).isEqualTo("dokari4")
        }
    }
}