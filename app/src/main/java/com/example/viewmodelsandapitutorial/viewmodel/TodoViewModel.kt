package com.example.viewmodelsandapitutorial.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viewmodelsandapitutorial.api.ApiService
import com.example.viewmodelsandapitutorial.api.Todo
import kotlinx.coroutines.launch
import java.lang.Exception

class TodoViewModel : ViewModel() {
    private val _todoList = mutableStateListOf<Todo>()
    // private var _todoList by remember { mutableStateListOf<Todo>() }
    var errorMessage: String by mutableStateOf("")
    val todoList: List<Todo>
        get() = _todoList

    fun getTodoList() {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()
            try {
                _todoList.clear()
                _todoList.addAll(apiService.getTodos())
            } catch (e: Exception){
                errorMessage = e.message.toString()
            }
        }
    }
}