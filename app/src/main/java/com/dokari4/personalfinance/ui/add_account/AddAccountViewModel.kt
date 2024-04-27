package com.dokari4.personalfinance.ui.add_account

import androidx.lifecycle.ViewModel
import com.dokari4.personalfinance.domain.model.Account
import com.dokari4.personalfinance.domain.usecase.AppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddAccountViewModel @Inject constructor(private val appUseCase: AppUseCase) : ViewModel() {
    fun insertAccount(account: Account) = appUseCase.insertAccount(account)
}