package com.example.wharareyouupto2.ui.viewmodel

import android.app.Application
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.wharareyouupto2.data.db.MemoDatabase
import com.example.wharareyouupto2.data.model.Memo
import com.example.wharareyouupto2.data.repository.MemoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : MemoRepository
    private var textCounter = ""

    init{

        val memoDao = MemoDatabase.getDatabase(application)!!.memoDao()
        repository = MemoRepository(memoDao)

    }

    fun addMemo(memo : Memo){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMemo(memo)
        }
    }

    fun updateMemo(memo : Memo){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateMemo(memo)
        }
    }

    val myText = ObservableField<String>()

    fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        Log.w("tag", "onTextChanged $s")
        textCounter = s.toString().length.toString()

    }

    val textcount = textCounter

}
