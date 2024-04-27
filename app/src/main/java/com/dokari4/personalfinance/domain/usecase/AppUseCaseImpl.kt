package com.dokari4.personalfinance.domain.usecase

import com.dokari4.personalfinance.domain.model.Account
import com.dokari4.personalfinance.domain.model.User
import com.dokari4.personalfinance.domain.repository.IAppRepository
import io.reactivex.Flowable
import javax.inject.Inject

class AppUseCaseImpl @Inject constructor(private val appRepository: IAppRepository) : AppUseCase {

    override fun getAccountList(): Flowable<List<Account>> = appRepository.getAccountList()

    override fun insertAccount(account: Account) = appRepository.insertAccount(account)
    override fun insertUser(user: User) = appRepository.insertUser(user)
}