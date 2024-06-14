package com.dokari4.core.domain.usecase.home

import com.dokari4.core.domain.repository.AppRepository
import javax.inject.Inject

class CheckOnboardingStateUseCase @Inject constructor(private val appRepository: AppRepository) {
    operator fun invoke() = appRepository.checkOnboardingState()

}