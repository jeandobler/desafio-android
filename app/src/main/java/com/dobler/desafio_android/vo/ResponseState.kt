package com.dobler.desafio_android.vo

sealed class ResponseState<T>

class Success<T>(val response: T) : ResponseState<T>()
class Error<T>(val response: String) : ResponseState<T>()
class Loading<T> : ResponseState<T>()