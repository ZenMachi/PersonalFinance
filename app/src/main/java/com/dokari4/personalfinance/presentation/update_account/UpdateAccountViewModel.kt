package com.dokari4.personalfinance.presentation.update_account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dokari4.personalfinance.domain.model.Account
import com.dokari4.personalfinance.domain.usecase.AppUseCase
import com.dokari4.personalfinance.domain.usecase.account.DeleteAccountUseCase
import com.dokari4.personalfinance.domain.usecase.account.UpdateAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class UpdateAccountViewModel @Inject constructor(
    private val updateAccountUseCase: UpdateAccountUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase
) : ViewModel() {

    private val _accountId = MutableStateFlow(0)
    private val _name = MutableStateFlow("")
    private val _amount = MutableStateFlow("")
    private val _selectionType = MutableStateFlow("")

    val accountId = _accountId.asStateFlow()
    val name = _name.asStateFlow()
    val amount = _amount.asStateFlow()
    val selectionType = _selectionType.asStateFlow()

    suspend fun updateAccount() = viewModelScope.async {
        val account = Account(
            id = accountId.value,
            userId = 1,
            name = name.value,
            accountType = selectionType.value,
            amount = amount.value.toDouble(),
        )
        updateAccountUseCase(account)
    }

    suspend fun deleteAccount() = viewModelScope.async {
        val account = Account(
            id = accountId.value,
            userId = 1,
            name = name.value,
            accountType = selectionType.value,
            amount = amount.value.toDouble(),
        )
        deleteAccountUseCase(account)
    }

    fun updateEditTextName(name: String) {
        _name.value = name
    }

    fun updateEditTextAmount(amount: String) {
        _amount.value = amount
    }

    fun updateSelectionType(selection: String) {
        _selectionType.value = selection
    }

    fun updateAccountId(id: Int) {
        _accountId.value = id
    }

    fun isValid(): Flow<Boolean> {
        return combine(name, amount, selectionType) { name, amount, selectionType ->
            name.isNotEmpty() && amount.isNotEmpty() && selectionType.isNotEmpty()
        }
    }

}