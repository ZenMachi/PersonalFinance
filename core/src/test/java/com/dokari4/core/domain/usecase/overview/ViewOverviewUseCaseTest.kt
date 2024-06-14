package com.dokari4.core.domain.usecase.overview

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dokari4.core.domain.model.Category
import com.dokari4.core.domain.repository.FakeAppRepository
import com.dokari4.core.domain.usecase.category.ViewCategoryUseCase
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ViewOverviewUseCaseTest {

    private lateinit var repository: FakeAppRepository
    private lateinit var viewOverviewUseCase: ViewOverviewUseCase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        repository = FakeAppRepository()
        viewOverviewUseCase = ViewOverviewUseCase(repository)
    }

    @Test
    fun `input income overview, return name Income User`() = runBlocking {
        viewOverviewUseCase("income").collect {
            val result = it.find { it.name == "Income User" }

            Truth.assertThat(result?.name).isEqualTo("Income User")
        }
    }

    @Test
    fun `input expense overview, return name Expense User`() = runBlocking {
        viewOverviewUseCase("expense").collect {
            val result = it.find { it.name == "Expense User" }

            Truth.assertThat(result?.name).isEqualTo("Expense User")
        }
    }
}