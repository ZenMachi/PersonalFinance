package com.dokari4.personalfinance.domain.usecase.transaction

import com.dokari4.personalfinance.domain.repository.AppRepository
import javax.inject.Inject

class ViewTransactionUseCase @Inject constructor(private val appRepository: AppRepository) {
    operator fun invoke() = appRepository.getTransactionList()
}