package com.dokari4.core.domain.usecase.home

import com.dokari4.core.domain.repository.FakeAppRepository
import com.dokari4.core.domain.usecase.register.SetOnboardingUseCase
import com.dokari4.core.util.enums.OnboardingState
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class CheckOnboardingStateUseCaseTest {
    private lateinit var repository: FakeAppRepository
    private lateinit var checkOnboardingStateUseCase: CheckOnboardingStateUseCase

    @Before
    fun setUp() {
        repository = FakeAppRepository()
        checkOnboardingStateUseCase = CheckOnboardingStateUseCase(repository)
    }

    @Test
    fun `check onboarding state, return NOT_DONE`() = runBlocking {
        checkOnboardingStateUseCase().collect {
            Truth.assertThat(it).isEqualTo(OnboardingState.NOT_DONE)
        }
    }
}