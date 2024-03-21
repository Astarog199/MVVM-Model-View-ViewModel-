package com.example.lesson12.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "MaimViewModel"

class MainViewModel:ViewModel() {

    // присваиваем дефолтное состояние
    private val _state = MutableStateFlow<MyState>(MyState.Success)
    val state = _state.asStateFlow() // отслеживает состояние из View

    init {
        Log.d(TAG, "init: ")
    }

    fun onButtonClick(){
        Log.d(TAG, "onButtonClick: ")
    }

    override fun onCleared(){
        super.onCleared()
        Log.d(TAG, "onCleared: ")
    }

    fun onSignInClick(login:String, password: String){
        viewModelScope.launch {
            _state.value = MyState.Loading
            Log.d(TAG, "onSignInClick: Loading")
            delay(10000)
            _state.value = MyState.Success
            Log.d(TAG, "onSignInClick: Success")
        }
    }

}