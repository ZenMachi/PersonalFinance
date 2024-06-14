package com.dokari4.core.domain.usecase.register

import com.dokari4.core.domain.model.User
import com.dokari4.core.domain.repository.AppRepository
import javax.inject.Inject

class AddUserUseCase @Inject constructor(private val appRepository: AppRepository) {
    suspend operator fun invoke(user: User) = appRepository.insertUser(user)

}