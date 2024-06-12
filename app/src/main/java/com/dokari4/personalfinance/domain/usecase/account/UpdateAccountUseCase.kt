package com.dokari4.personalfinance.domain.usecase.account

import com.dokari4.personalfinance.domain.model.Account
import com.dokari4.personalfinance.domain.repository.AppRepository
import javax.inject.Inject

class UpdateAccountUseCase @Inject constructor(private val appRepository: AppRepository) {
    suspend operator fun invoke(account: Account) = appRepository.updateAccount(account)

}