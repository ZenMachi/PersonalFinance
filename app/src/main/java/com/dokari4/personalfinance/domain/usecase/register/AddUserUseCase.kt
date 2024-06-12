package com.dokari4.personalfinance.domain.usecase.register

import com.dokari4.personalfinance.domain.model.User
import com.dokari4.personalfinance.domain.repository.AppRepository
import javax.inject.Inject

class AddUserUseCase @Inject constructor(private val appRepository: AppRepository) {
    suspend operator fun invoke(user: User) = appRepository.insertUser(user)

}