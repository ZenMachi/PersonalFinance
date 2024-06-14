package com.dokari4.core.domain.usecase.overview

import com.dokari4.core.domain.repository.AppRepository
import javax.inject.Inject

class ViewOverviewUseCase @Inject constructor(private val appRepository: AppRepository) {
    operator fun invoke(type: String) = appRepository.getCategoryTotalTransaction(type)
}