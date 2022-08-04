package com.example.wharareyouupto2.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DoneListViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is donelist Fragment"
    }
    val text: LiveData<String> = _text
}