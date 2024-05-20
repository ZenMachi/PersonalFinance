package com.dokari4.personalfinance.ui.overview

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dokari4.personalfinance.domain.model.CategoryCountTotal
import com.dokari4.personalfinance.domain.usecase.AppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(private val appUseCase: AppUseCase) : ViewModel() {
    private val _typeIncome = MutableStateFlow<List<CategoryCountTotal>>(emptyList())
    private val _typeExpense = MutableStateFlow<List<CategoryCountTotal>>(emptyList())

    val typeIncome = _typeIncome.asStateFlow()
    val typeExpense = _typeExpense.asStateFlow()

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

    fun isContentEmpty(type: List<CategoryCountTotal>): Flow<Int> {
        return flow {
            if (type.isEmpty()) {
                emit(View.VISIBLE)
            } else {
                emit(View.GONE)
            }

        }
    }
}