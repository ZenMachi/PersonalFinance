package com.dokari4.personalfinance.presentation.onboarding

import android.text.Editable
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dokari4.personalfinance.domain.model.Account
import com.dokari4.personalfinance.domain.model.Category
import com.dokari4.personalfinance.domain.model.User
import com.dokari4.personalfinance.domain.usecase.account.AddAccountUseCase
import com.dokari4.personalfinance.domain.usecase.category.AddCategoryUseCase
import com.dokari4.personalfinance.domain.usecase.register.AddUserUseCase
import com.dokari4.personalfinance.domain.usecase.register.SetOnboardingUseCase
import com.dokari4.personalfinance.util.enums.OnboardingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val addAccountUseCase: AddAccountUseCase,
    private val addCategoryUseCase: AddCategoryUseCase,
    private val addUserUseCase: AddUserUseCase,
    private val setOnboardingUseCase: SetOnboardingUseCase,
): ViewModel() {

    private val _categoryChecked = MutableStateFlow(false)
    val categoryChecked = _categoryChecked.asStateFlow()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    suspend fun setOnboardingState(state: OnboardingState) = viewModelScope.async {
        setOnboardingUseCase(state)
    }
    suspend fun insertUser(user: User) = viewModelScope.async {
        addUserUseCase(user)
    }
    suspend fun insertCategory(category: Category) = viewModelScope.async {
        addCategoryUseCase(category)
    }

    suspend fun insertAccount(account: Account) = viewModelScope.async {
        addAccountUseCase(account)
    }

    fun isValidName(): Flow<Boolean> {
        return combine(name) { name ->
            name.isNotEmpty()
        }
    }

    fun isValidCategory(): Flow<Boolean> {
        return combine(categoryChecked) { selectedCategory ->
            selectedCategory.isNotEmpty()
        }

    }

    fun setValidCategory(selected: Boolean) {
        _categoryChecked.value = selected
    }

    fun addEditTextNameListener(editable: Editable?) {
        _name.value = editable.toString()
        Log.d("OnboardingViewModel", "addEditTextNameListener: ${name.value}")
    }
}