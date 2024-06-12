package com.dokari4.personalfinance.domain.usecase.category

import com.dokari4.personalfinance.domain.repository.AppRepository
import javax.inject.Inject

class ViewCategoryUseCase @Inject constructor(private val appRepository: AppRepository) {
    operator fun invoke() = appRepository.getCategoryList()
}