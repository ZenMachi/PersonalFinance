package com.dokari4.core.domain.usecase.category

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dokari4.core.domain.model.Category
import com.dokari4.core.domain.repository.FakeAppRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ViewCategoryUseCaseTest {

    private lateinit var repository: FakeAppRepository
    private lateinit var viewCategoryUseCase: ViewCategoryUseCase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    val categoryDummy = Category(id = 1, name = "Ricardo Thornton")

    @Before
    fun setUp() {
        repository = FakeAppRepository()
        viewCategoryUseCase = ViewCategoryUseCase(repository)
        runBlocking {
            repository.insertCategory(categoryDummy)
        }
    }

    @Test
    fun `view category, contain categoryDummy`() = runBlocking {
        viewCategoryUseCase().collect {
            Truth.assertThat(it).contains(categoryDummy)
        }
    }
}