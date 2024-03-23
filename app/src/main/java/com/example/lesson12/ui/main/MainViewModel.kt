package com.example.lesson12.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

private const val TAG = "MaimViewModel"


class MainViewModel(private val repository: MainRepository):ViewModel() {

    // присваиваем дефолтное состояние
    private val _state: MutableStateFlow<MyState> = MutableStateFlow(MyState.Success)
    val state = _state.asStateFlow() // отслеживает состояние из View

    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()

    init {
        Log.d(TAG, "init: ${state.value}")
    }

    override fun onCleared(){
        super.onCleared()
        Log.d(TAG, "onCleared: ")
    }

    /**
     * Метод onSignInClick, вызывается при нажатии кнопки.
     * Метод проверяет, не пустые ли поля ввода (login и password),
     * и если одно из них пустое, отправляет сообщение об ошибке.
     *
     * Если оба поля не пусты, устанавливает состояние Loading и пытается получить данные из репозитория.
     *
     * Если происходит исключение, устанавливает состояние Error и отправляет сообщение об ошибке.
     *
     * Если данные успешно получены, устанавливает состояние в MyState.Success.
     */

    fun onSignInClick(login:String, password: String){
        viewModelScope.launch {
            val isLoginEmpty = login.isBlank()
            val isPasswordEmpty = password.isBlank()
            if (isLoginEmpty || isPasswordEmpty) {
                var loginError: String? = null
                if (isLoginEmpty){
                    loginError = "login is required"
                }

                var passwordError: String? = null
                if (isPasswordEmpty){
                    passwordError = "Password is required"
                }
                _state.value = MyState.Error(loginError, passwordError)
                _error.send("Login or Password not valid")
            } else {
                _state.value = MyState.Loading
                Log.d(TAG, "onSignInClick: Loading")
                try {
                    repository.getData()
                    _state.value = MyState.Success
                    Log.d(TAG, "onSignInClick: Success")
                }catch (e:Exception){
                    _error.send(e.toString())
                    _state.value= MyState.Error(null, null)
                }
            }
        }
    }
}