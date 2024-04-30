package com.dokari4.personalfinance.data

sealed class State {
    data object Loading: State()
    class Success(val successMessage: String) : State()
    class Error(val error: Throwable?): State()
}