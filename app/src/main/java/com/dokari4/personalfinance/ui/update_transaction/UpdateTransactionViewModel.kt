package com.dokari4.personalfinance.ui.update_transaction

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dokari4.personalfinance.domain.model.Transaction
import com.dokari4.personalfinance.domain.usecase.AppUseCase
import com.dokari4.personalfinance.util.DateConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class UpdateTransactionViewModel @Inject constructor(private val appUseCase: AppUseCase) :
    ViewModel() {
    private val _transactionId = MutableStateFlow<Int?>(null)
    private val _name = MutableStateFlow("")
    private val _description = MutableStateFlow("")
    private val _amount = MutableStateFlow("")
    private val _date = MutableStateFlow("")
    private val _time = MutableStateFlow("")
    private val _accountId = MutableStateFlow<Int?>(null)
    private val _transactionType = MutableStateFlow("")
    private val _categoryId = MutableStateFlow<Int?>(null)

    private val transactionId = _transactionId.asStateFlow()
    val name = _name.asStateFlow()
    val description = _description.asStateFlow()
    val amount = _amount.asStateFlow()
    val date = _date.asStateFlow()
    val time = _time.asStateFlow()
    val accountId = _accountId.asStateFlow()
    val transactionType = _transactionType.asStateFlow()
    val categoryId = _categoryId.asStateFlow()

    val getAccounts = appUseCase.getAccountList()
    val getCategories = appUseCase.getCategoryList()
    suspend fun insertTransaction(transaction: Transaction) = viewModelScope.async {
        appUseCase.insertTransaction(transaction)
    }

    suspend fun updateTransaction() = viewModelScope.async {
        val dateTime = DateConverter.setDateAndTimeToLong(date = date.value, time = time.value)
        val transaction = Transaction(
            id = transactionId.value!!,
            accountId = accountId.value!!,
            categoryId = categoryId.value!!,
            name = name.value,
            description = description.value,
            amount = amount.value.toDouble(),
            dateTime = dateTime,
            type = transactionType.value
        )
        appUseCase.updateTransaction(transaction)
    }

    fun updateTransactionId(id: Int?) {
        _transactionId.value = id
    }

    fun updateEditTextName(value: String) {
        _name.value = value
    }

    fun updateEditTextDescription(value: String) {
        _description.value = value
    }

    fun updateEditTextAmount(value: String) {
        _amount.value = value
    }

    fun updateButtonDateValue(value: String) {
        _date.value = value
    }

    fun updateButtonTimeValue(value: String) {
        _time.value = value
    }

    fun updateAccountId(id: Int) {
        _accountId.value = id
        Log.d("Updated ViewModel", "AccountId: ${_accountId.value}")
    }

    fun updateTransactionType(selection: String) {
        _transactionType.value = selection
    }

    fun updateCategory(selectionId: Int?) {
        _categoryId.value = selectionId
    }

    fun isValid(): Flow<Boolean> {

        val isEditTextsValid = combine(name, description, amount) { name, description, amount ->
            name.isNotEmpty() && description.isNotEmpty() && amount.isNotEmpty()
        }
        val isDateAndTimeValid = combine(date, time) { date, time ->
            date.isNotEmpty() && time.isNotEmpty()
        }
        val isIdValid = combine(accountId, categoryId) { accountId, categoryId ->
            accountId != null && categoryId != null
        }

        return combine(
            isEditTextsValid,
            isDateAndTimeValid,
            isIdValid,
            transactionType
        ) { isValid1, isValid2, isValid3, transactionType ->
            isValid1 && isValid2 && isValid3 && transactionType.isNotEmpty()
        }
    }
}