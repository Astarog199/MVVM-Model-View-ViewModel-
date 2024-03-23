package com.example.lesson12.ui.main

import kotlinx.coroutines.delay
import java.net.ConnectException

class MainRepository {

    private var count = 0

    suspend fun getData(): String{
        delay(15000)
        return if (++count %2 ==0){
            throw ConnectException("No Internet Connection")
        } else{
            "Done"
        }
    }
}