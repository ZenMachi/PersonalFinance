package com.dokari4.core.data

sealed class State<T>(val data: T? = null, val msg: String? = null) {
    class HasData<T>(data: T) : com.dokari4.core.data.State<T>(data)
    class NoData<T>(data: T? = null) : com.dokari4.core.data.State<T>(data)
    class Error<T>(message: String ,data: T? = null): com.dokari4.core.data.State<T>(data, message)
}