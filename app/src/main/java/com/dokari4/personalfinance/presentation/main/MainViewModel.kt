package com.dokari4.personalfinance.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dokari4.core.domain.usecase.home.CheckOnboardingStateUseCase
import com.dokari4.core.util.enums.OnboardingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val checkOnboardingStateUseCase: CheckOnboardingStateUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    val checkOnboardingState = checkOnboardingStateUseCase()

    init {
        viewModelScope.launch {
            isLoaded().collect {
                _isLoading.value = !it
            }
        }
    }

    private fun isLoaded() = flow {
        emit(false)
        checkOnboardingState.collect {
            when (it) {
                OnboardingState.DONE -> {
                    emit(true)
                }

                OnboardingState.NOT_DONE ->  {
                    emit(true)
                }
            }
        }
    }
}