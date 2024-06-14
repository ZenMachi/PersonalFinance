package com.dokari4.core.domain.usecase.transaction

import com.dokari4.core.domain.model.Transaction
import com.dokari4.core.domain.repository.AppRepository
import javax.inject.Inject

class TransferTransactionUseCase @Inject constructor(private val appRepository: AppRepository) {
    suspend operator fun invoke(from: Transaction, to: Transaction) = appRepository.transferTransaction(from, to)
}