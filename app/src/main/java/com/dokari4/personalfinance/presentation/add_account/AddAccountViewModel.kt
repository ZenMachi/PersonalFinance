package com.dokari4.personalfinance.presentation.add_account

import android.text.Editable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dokari4.core.domain.model.Account
import com.dokari4.core.domain.usecase.account.AddAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class AddAccountViewModel @Inject constructor(
    private val addAccountUseCase: AddAccountUseCase
    ) : ViewModel() {

    private val _name = MutableStateFlow("")
    private val _amount = MutableStateFlow("")
    private val _selectionType = MutableStateFlow("")

    private val name = _name.asStateFlow()
    private val amount = _amount.asStateFlow()
    private val selectionType = _selectionType.asStateFlow()

    suspend fun insertAccount() = viewModelScope.async {
        val account = Account(
            userId = 1,
            accountType = selectionType.value,
            name = name.value,
            amount = amount.value.toDouble()
        )
        addAccountUseCase(account)
    }

    fun addEditTextNameListener(editable: Editable?) {
        _name.value = editable.toString()
    }

    fun addEditTextAmountListener(editable: Editable?) {
        _amount.value = editable.toString()
    }

    fun updateSelectionType(selection: String) {
        _selectionType.value = selection
    }

    fun isValid(): Flow<Boolean> {
        return combine(name, amount, selectionType) { name, amount, selectionType ->
            name.isNotEmpty() && amount.isNotEmpty() && selectionType.isNotEmpty()
        }
    }
}