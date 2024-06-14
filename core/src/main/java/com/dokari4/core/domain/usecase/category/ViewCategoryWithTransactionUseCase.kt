package com.dokari4.core.domain.usecase.category

import com.dokari4.core.domain.repository.AppRepository
import javax.inject.Inject

class ViewCategoryWithTransactionUseCase @Inject constructor(private val appRepository: AppRepository) {
    operator fun invoke(type: String) = appRepository.getCategoryTotalTransaction(type)
}