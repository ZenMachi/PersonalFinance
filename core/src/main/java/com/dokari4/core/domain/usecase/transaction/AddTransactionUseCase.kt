package com.dokari4.core.domain.usecase.transaction

import com.dokari4.core.domain.model.Transaction
import com.dokari4.core.domain.repository.AppRepository
import javax.inject.Inject

class AddTransactionUseCase @Inject constructor(private val appRepository: AppRepository) {
    suspend operator fun invoke(transaction: Transaction) = appRepository.insertTransaction(transaction)
}