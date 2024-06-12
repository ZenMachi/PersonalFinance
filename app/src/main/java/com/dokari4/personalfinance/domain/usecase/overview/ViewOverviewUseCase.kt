package com.dokari4.personalfinance.domain.usecase.overview

import com.dokari4.personalfinance.domain.repository.AppRepository
import javax.inject.Inject

class ViewOverviewUseCase @Inject constructor(private val appRepository: AppRepository) {
    operator fun invoke(type: String) = appRepository.getCategoryTotalTransaction(type)
}