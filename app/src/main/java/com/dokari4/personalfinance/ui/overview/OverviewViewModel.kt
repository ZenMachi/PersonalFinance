package com.dokari4.personalfinance.ui.overview

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.toLiveData
import androidx.lifecycle.viewModelScope
import com.dokari4.personalfinance.domain.model.CategoryCountTotal
import com.dokari4.personalfinance.domain.usecase.AppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(private val appUseCase: AppUseCase) : ViewModel() {
    private val _typeIncome = MutableStateFlow<List<CategoryCountTotal>>(emptyList())
    private val _typeExpense = MutableStateFlow<List<CategoryCountTotal>>(emptyList())

    val typeIncome = _typeIncome.asStateFlow()
    val typeExpense= _typeExpense.asStateFlow()

    val getCategoryTotalIncome = appUseCase.getCategoryTotalTransaction("Income")
    val getCategoryTotalExpense = appUseCase.getCategoryTotalTransaction("Expense")



    fun getOverviewTypeIncome() {
        viewModelScope.launch {
            appUseCase.getCategoryTotalTransaction("Income").collect {
                _typeIncome.value = it
            }
        }

    }
    fun getOverviewTypeExpense() {
        viewModelScope.launch {
            appUseCase.getCategoryTotalTransaction("Expense").collect {
                _typeExpense.value = it
            }
        }
    }
}