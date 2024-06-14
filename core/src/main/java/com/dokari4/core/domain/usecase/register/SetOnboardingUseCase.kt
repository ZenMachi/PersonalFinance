package com.dokari4.core.domain.usecase.register

import com.dokari4.core.domain.repository.AppRepository
import com.dokari4.core.util.enums.OnboardingState
import javax.inject.Inject

class SetOnboardingUseCase @Inject constructor(private val appRepository: AppRepository) {
    suspend operator fun invoke(state: OnboardingState) {
        appRepository.setOnboardingState(state)
    }

}