package com.dokari4.personalfinance.presentation.main

import androidx.lifecycle.ViewModel
import com.dokari4.core.domain.usecase.AppUseCase
import com.dokari4.core.domain.usecase.home.CheckOnboardingStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val checkOnboardingStateUseCase: CheckOnboardingStateUseCase
) : ViewModel() {

    val checkOnboardingState = checkOnboardingStateUseCase()
}