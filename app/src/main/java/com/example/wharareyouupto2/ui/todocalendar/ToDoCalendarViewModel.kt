package com.example.wharareyouupto2.ui.todocalendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ToDoCalendarViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is todocalendar Fragment"
    }
    val text: LiveData<String> = _text
}