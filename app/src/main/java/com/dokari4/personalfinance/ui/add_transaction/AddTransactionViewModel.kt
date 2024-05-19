package com.dokari4.personalfinance.ui.add_transaction

import android.text.Editable
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
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
class AddTransactionViewModel
@Inject constructor(private val appUseCase: AppUseCase) : ViewModel() {
    private val _name = MutableStateFlow("")
    private val _description = MutableStateFlow("")
    private val _amount = MutableStateFlow("")
    private val _date = MutableStateFlow("")
    private val _time = MutableStateFlow("")
    private val _accountId = MutableStateFlow<Int?>(null)
    private val _transactionType = MutableStateFlow("")
    private val _categoryId = MutableStateFlow<Int?>(null)

    private val name = _name.asStateFlow()
    private val description = _description.asStateFlow()
    private val amount = _amount.asStateFlow()
    val date = _date.asStateFlow()
    val time = _time.asStateFlow()
    private val accountId = _accountId.asStateFlow()
    private val transactionType = _transactionType.asStateFlow()
    private val categoryId = _categoryId.asStateFlow()

    val getAccounts = appUseCase.getAccountList()
    val getCategories = appUseCase.getCategoryList()
    suspend fun insertTransaction(transaction: Transaction) = viewModelScope.async {
        appUseCase.insertTransaction(transaction)
    }

    suspend fun insertTransactionTest() = viewModelScope.async {
        val dateTime = DateConverter.setDateAndTimeToLong(date = date.value, time = time.value)
        val transaction = Transaction(
            accountId = accountId.value!!,
            categoryId = categoryId.value!!,
            name = name.value,
            description = description.value,
            amount = amount.value.toDouble(),
            dateTime = dateTime,
            type = transactionType.value
        )
        appUseCase.insertTransaction(transaction)
    }

    fun addEditTextNameListener(editable: Editable?) {
        _name.value = editable.toString()
    }

    fun addEditTextDescriptionListener(editable: Editable?) {
        _description.value = editable.toString()
    }

    fun addEditTextAmountListener(editable: Editable?) {
        _amount.value = editable.toString()
    }

    fun addButtonDateListener(editable: Editable?) {
        _date.value = editable.toString()
    }

    fun addButtonTimeListener(editable: Editable?) {
        _time.value = editable.toString()
    }

    fun updateDate(date: String) {
        _date.value = date
    }

    fun updateTime(time: String) {
        _time.value = time
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