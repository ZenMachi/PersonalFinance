package com.dokari4.core.domain.usecase.account

import com.dokari4.core.domain.model.Account
import com.dokari4.core.domain.repository.AppRepository
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(private val appRepository: AppRepository) {
    suspend operator fun invoke(account: Account) = appRepository.deleteAccount(account)
}