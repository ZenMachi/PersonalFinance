package com.dokari4.personalfinance.domain.usecase.account

import com.dokari4.personalfinance.domain.model.AccountWithTransactions
import com.dokari4.personalfinance.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ViewAccountWithTransactionUseCase @Inject constructor(private val appRepository: AppRepository) {
    operator fun invoke(): Flow<List<AccountWithTransactions>> = appRepository.getAccountsWithTransactions()
}