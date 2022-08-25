package com.example.shemajamebeli_volsaboloo

sealed class ResponseState {
    class Success<T>(val result: T): ResponseState()
    class Loader(val isLoading: Boolean): ResponseState()
    class Error(val error: String): ResponseState()
}