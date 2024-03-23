package com.example.lesson12.ui.main

/**
 *  Закрытый класс MyState:
 *  содержит три объекта: Loading, Success и Error.
 *
 *  Error является классом MyState и содержит два поля: loginError и passwordError.
 */
sealed class MyState {
    data object Loading : MyState()
    data object Success : MyState()
    data class Error(
        val loginError: String?,
        val passwordError: String?
        ): MyState()
}