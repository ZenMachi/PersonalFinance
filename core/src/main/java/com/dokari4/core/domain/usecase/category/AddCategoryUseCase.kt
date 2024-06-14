package com.dokari4.core.domain.usecase.category

import com.dokari4.core.domain.model.Category
import com.dokari4.core.domain.repository.AppRepository
import javax.inject.Inject

class AddCategoryUseCase @Inject constructor(private val appRepository: AppRepository) {
    suspend operator fun invoke(category: Category) = appRepository.insertCategory(category)

}