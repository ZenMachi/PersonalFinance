package com.dokari4.personalfinance.presentation.add_transaction

import android.text.Editable
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dokari4.core.domain.model.Account
import com.dokari4.core.domain.model.Transaction
import com.dokari4.core.domain.usecase.account.ViewAccountUseCase
import com.dokari4.core.domain.usecase.category.ViewCategoryUseCase
import com.dokari4.core.domain.usecase.transaction.AddTransactionUseCase
import com.dokari4.core.domain.usecase.transaction.TransferTransactionUseCase
import com.dokari4.core.util.DateConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val addTransactionUseCase: AddTransactionUseCase,
    private val viewAccountUseCase: ViewAccountUseCase,
    private val viewCategoryUseCase: ViewCategoryUseCase,
    private val transferTransactionUseCase: TransferTransactionUseCase
) : ViewModel() {
    private val _name = MutableStateFlow("")
    private val _description = MutableStateFlow("")
    private val _amount = MutableStateFlow("")
    private val _date = MutableStateFlow("")
    private val _time = MutableStateFlow("")
    private val _accountIdStart = MutableStateFlow<Int?>(null)
    private val _accountIdDest = MutableStateFlow<Int?>(null)
    private val _transactionType = MutableStateFlow("")
    private val _categoryId = MutableStateFlow<Int?>(null)

    val name = _name.asStateFlow()
    val description = _description.asStateFlow()
    val amount = _amount.asStateFlow()
    val date = _date.asStateFlow()
    val time = _time.asStateFlow()
    val accountIdStart = _accountIdStart.asStateFlow()
    val accountIdDest = _accountIdDest.asStateFlow()
    private val transactionType = _transactionType.asStateFlow()
    val categoryId = _categoryId.asStateFlow()

    private val arrayListAccount = ArrayList<Account>()

    val getAccounts = viewAccountUseCase()
    val getCategories = viewCategoryUseCase()
    suspend fun transferTransaction(accounts: List<Account>) = viewModelScope.async {
        val dateTime = DateConverter.setDateAndTimeToLong(date = date.value, time = time.value)
        val filteredAccountFrom = accounts.find { it.id == accountIdStart.value }
        val filteredAccountTo = accounts.find { it.id == accountIdDest.value }
        val accountTypeFrom = filteredAccountFrom?.accountType
        val accountTypeTo = filteredAccountTo?.accountType

        val from = Transaction(
            accountId = accountIdStart.value!!,
            categoryId = categoryId.value!!,
            name = "Transfer from $accountTypeFrom to $accountTypeTo",
            description = "",
            amount = amount.value.toDouble(),
            dateTime = dateTime,
            type = "Expense"
        )
        val to = Transaction(
            accountId = accountIdDest.value!!,
            categoryId = categoryId.value!!,
            name = "Received from $accountTypeFrom to $accountTypeTo",
            description = "",
            amount = amount.value.toDouble(),
            dateTime = dateTime,
            type = "Income"
        )

        transferTransactionUseCase(from, to)
    }

    suspend fun insertTransaction() = viewModelScope.async {
        val dateTime = DateConverter.setDateAndTimeToLong(date = date.value, time = time.value)
        val transaction = Transaction(
            accountId = accountIdStart.value!!,
            categoryId = categoryId.value!!,
            name = name.value,
            description = description.value,
            amount = amount.value.toDouble(),
            dateTime = dateTime,
            type = transactionType.value
        )
        addTransactionUseCase(transaction)
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

    fun updateAccountIdStart(id: Int) {
        _accountIdStart.value = id
        Log.d("Updated ViewModel", "AccountId: ${_accountIdStart.value}")
    }

    fun updateAccountIdDest(id: Int) {
        _accountIdDest.value = id
        Log.d("Updated ViewModel", "AccountIdDest: ${_accountIdDest.value}")
    }

    fun updateTransactionType(selection: String) {
        _transactionType.value = selection
    }

    fun updateCategory(selectionId: Int?) {
        _categoryId.value = selectionId
    }

    fun isValidIncomeExpense(): Flow<Boolean> {

        val isEditTextsValid = combine(name, description, amount) { name, description, amount ->
            name.isNotEmpty() && description.isNotEmpty() && amount.isNotEmpty()
        }
        val isDateAndTimeValid = combine(date, time) { date, time ->
            date.isNotEmpty() && time.isNotEmpty()
        }
        val isIdValid = combine(accountIdStart, categoryId) { accountId, categoryId ->
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

    fun isValidTransfer(): Flow<Boolean> {

        val isEditTextsValid = combine(amount) { amount ->
            amount.isNotEmpty()
        }
        val isDateAndTimeValid = combine(date, time) { date, time ->
            date.isNotEmpty() && time.isNotEmpty()
        }
        val isIdValid = combine(
            accountIdStart,
            accountIdDest,
            categoryId
        ) { accountIdStart, accountIdDest, categoryId ->
            accountIdStart != null && categoryId != null && accountIdDest != null && accountIdStart != accountIdDest
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