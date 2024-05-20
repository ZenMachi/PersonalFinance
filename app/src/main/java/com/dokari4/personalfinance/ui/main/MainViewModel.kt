package com.dokari4.personalfinance.ui.main

import androidx.lifecycle.ViewModel
import com.dokari4.personalfinance.domain.usecase.AppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val appUseCase: AppUseCase) : ViewModel() {

    val checkOnboardingState = appUseCase.checkOnboardingState()
}