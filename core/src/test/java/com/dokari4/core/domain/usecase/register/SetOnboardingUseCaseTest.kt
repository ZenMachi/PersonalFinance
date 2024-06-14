package com.dokari4.core.domain.usecase.register

import com.dokari4.core.domain.repository.FakeAppRepository
import com.dokari4.core.util.enums.OnboardingState
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class SetOnboardingUseCaseTest {

    private lateinit var repository: FakeAppRepository
    private lateinit var setOnboardingUseCase: SetOnboardingUseCase


    @Before
    fun setUp() {
        repository = FakeAppRepository()
        setOnboardingUseCase = SetOnboardingUseCase(repository)
    }

    @Test
    fun `set onboarding state, return done`() = runBlocking {
        setOnboardingUseCase(OnboardingState.DONE)

        repository.checkOnboardingState().collect {
            Truth.assertThat(it).isEqualTo(OnboardingState.DONE)
        }
    }
}