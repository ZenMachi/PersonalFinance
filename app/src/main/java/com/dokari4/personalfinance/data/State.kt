package com.dokari4.personalfinance.data

sealed class State<T>(val data: T? = null, val msg: String? = null) {
    class HasData<T>(data: T) : State<T>(data)
    class NoData<T>(data: T? = null) : State<T>(data)
    class Error<T>(message: String ,data: T? = null): State<T>(data, message)
}