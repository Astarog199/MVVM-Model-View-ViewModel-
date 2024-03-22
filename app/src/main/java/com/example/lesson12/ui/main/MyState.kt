package com.example.lesson12.ui.main

sealed class MyState {
    data object Loading : MyState()
    data object Success : MyState()
    data class Error(
        val loginError: String?,
        val passwordError: String?
        ): MyState()
}