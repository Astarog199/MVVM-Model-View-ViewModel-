package com.example.lesson12.ui.main

sealed class MyState {
    data object Loading : MyState()
    data object Success : MyState()
}