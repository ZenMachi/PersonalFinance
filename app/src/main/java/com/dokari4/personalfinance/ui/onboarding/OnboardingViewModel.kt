package com.dokari4.personalfinance.ui.onboarding

import android.text.Editable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dokari4.personalfinance.domain.model.Category
import com.dokari4.personalfinance.domain.model.User
import com.dokari4.personalfinance.domain.usecase.AppUseCase
import com.dokari4.personalfinance.util.OnboardingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(private val appUseCase: AppUseCase): ViewModel() {

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    val checkOnboardingState = appUseCase.checkOnboardingState()

    suspend fun setOnboardingState(state: OnboardingState) = viewModelScope.async {
        appUseCase.setOnboardingState(state)
    }
    fun insertUser(user: User) = appUseCase.insertUser(user)
    fun insertCategory(category: Category) = appUseCase.insertCategory(category)

    fun addEditTextNameListener(editable: Editable?) {
        _name.value = editable.toString()
    }
}