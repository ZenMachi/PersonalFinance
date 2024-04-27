package com.dokari4.personalfinance.ui.accounts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.dokari4.personalfinance.domain.model.Account
import com.dokari4.personalfinance.domain.model.User
import com.dokari4.personalfinance.domain.usecase.AppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(private val appUseCase: AppUseCase) : ViewModel() {

    val getAccounts = appUseCase.getAccountList().toLiveData()

    fun insertAccount(account: Account) = appUseCase.insertAccount(account)
    fun insertUser(user: User) = appUseCase.insertUser(user)
}