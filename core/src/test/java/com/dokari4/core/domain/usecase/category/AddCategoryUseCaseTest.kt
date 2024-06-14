package com.dokari4.core.domain.usecase.category

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dokari4.core.domain.model.Account
import com.dokari4.core.domain.model.Category
import com.dokari4.core.domain.repository.FakeAppRepository
import com.dokari4.core.domain.usecase.account.DeleteAccountUseCase
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddCategoryUseCaseTest {

    private lateinit var repository: FakeAppRepository
    private lateinit var addCategoryUseCase: AddCategoryUseCase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    val categoryDummy = Category(id = 1, name = "Ricardo Thornton")

    @Before
    fun setUp() {
        repository = FakeAppRepository()
        addCategoryUseCase = AddCategoryUseCase(repository)
    }

    @Test
    fun `add category, should return same with categoryDummy`() = runBlocking {
        addCategoryUseCase(categoryDummy)
        repository.getCategoryList().collect {
            Truth.assertThat(it).contains(categoryDummy)
        }
    }
}