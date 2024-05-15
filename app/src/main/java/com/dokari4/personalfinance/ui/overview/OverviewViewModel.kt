package com.dokari4.personalfinance.ui.overview

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.dokari4.personalfinance.domain.model.CategoryCountTotal
import com.dokari4.personalfinance.domain.usecase.AppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(private val appUseCase: AppUseCase) : ViewModel() {
    private val _typeIncome = MutableLiveData<List<CategoryCountTotal>>()
    private val _typeExpense = MutableLiveData<List<CategoryCountTotal>>()

    val typeIncome: LiveData<List<CategoryCountTotal>> = _typeIncome
    val typeExpense: LiveData<List<CategoryCountTotal>> = _typeExpense

    val getCategoryTotalIncome = appUseCase.getCategoryTotalTransaction("Income").toLiveData()
    val getCategoryTotalExpense = appUseCase.getCategoryTotalTransaction("Expense").toLiveData()


    fun getOverviewTypeIncome(lifecycleOwner: LifecycleOwner) {
        appUseCase.getCategoryTotalTransaction("Income").toLiveData().observe(lifecycleOwner)  {
            _typeIncome.value = it
        }
    }
    fun getOverviewTypeExpense(lifecycleOwner: LifecycleOwner) {
        appUseCase.getCategoryTotalTransaction("Expense").toLiveData().observe(lifecycleOwner)  {
            _typeExpense.value = it
        }
    }
}