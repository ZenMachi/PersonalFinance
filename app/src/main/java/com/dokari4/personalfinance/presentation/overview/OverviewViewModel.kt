package com.dokari4.personalfinance.presentation.overview

import android.view.View
import androidx.lifecycle.ViewModel
import com.dokari4.personalfinance.domain.model.CategoryCountTotal
import com.dokari4.personalfinance.domain.usecase.overview.ViewOverviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val viewOverviewUseCase: ViewOverviewUseCase
) : ViewModel() {
    private val _typeIncome = MutableStateFlow<List<CategoryCountTotal>>(emptyList())
    private val _typeExpense = MutableStateFlow<List<CategoryCountTotal>>(emptyList())

    val typeIncome = _typeIncome.asStateFlow()
    val typeExpense = _typeExpense.asStateFlow()

    val getCategoryTotalIncome = viewOverviewUseCase("Income")
    val getCategoryTotalExpense = viewOverviewUseCase("Expense")



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