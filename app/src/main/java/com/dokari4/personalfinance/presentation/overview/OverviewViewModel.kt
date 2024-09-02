package com.dokari4.personalfinance.presentation.overview

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dokari4.core.domain.model.CategoryCountTotal
import com.dokari4.core.domain.usecase.overview.ViewOverviewUseCase
import com.dokari4.personalfinance.presentation.overview.state.CategoryState
import com.dokari4.personalfinance.presentation.overview.state.OverviewUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(
    viewOverviewUseCase: ViewOverviewUseCase
) : ViewModel() {
    private val _incomeState = MutableStateFlow(OverviewUiState())
    private val _expenseState = MutableStateFlow(OverviewUiState())

    private val getCategoryTotalIncome = viewOverviewUseCase("Income")
    private val getCategoryTotalExpense = viewOverviewUseCase("Expense")

    val incomeState = _incomeState.asStateFlow()
    val expenseState = _expenseState.asStateFlow()

    init {
        viewModelScope.launch {
           getCategoryTotalIncome.collect { income ->
               val data = income.filter { it.count > 0 }
               _incomeState.value = OverviewUiState(
                   categories = mapValueToState(data),
                   isEmpty = data.isEmpty()
               )
               Log.d("OverviewViewModel", "incomeState: ${incomeState.value.isEmpty}")
           }
        }
        viewModelScope.launch {
            getCategoryTotalExpense.collect { expense ->
                val data = expense.filter { it.count > 0 }
                _expenseState.value = OverviewUiState(
                    categories = mapValueToState(data),
                    isEmpty = data.isEmpty()
                )
                Log.d("OverviewViewModel2", "expenseState: ${incomeState.value.isEmpty}")
            }
        }
    }

    private fun mapValueToState(value: List<CategoryCountTotal>): List<CategoryState> {
        return value.map {
            CategoryState(
                id = it.id,
                name = it.name,
                count = it.count,
                amount = it.amount.toBigDecimal()
            )
        }
    }

}