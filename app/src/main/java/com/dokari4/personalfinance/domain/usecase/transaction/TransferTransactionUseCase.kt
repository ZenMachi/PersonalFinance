package com.dokari4.personalfinance.domain.usecase.transaction

import com.dokari4.personalfinance.domain.model.Transaction
import com.dokari4.personalfinance.domain.repository.AppRepository
import javax.inject.Inject

class TransferTransactionUseCase @Inject constructor(private val appRepository: AppRepository) {
    suspend operator fun invoke(from: Transaction, to: Transaction) = appRepository.transferTransaction(from, to)
}