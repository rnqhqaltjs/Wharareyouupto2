package com.example.wharareyouupto2.ui.viewmodel

import android.app.Application
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.wharareyouupto2.data.db.MemoDatabase
import com.example.wharareyouupto2.data.model.Memo
import com.example.wharareyouupto2.data.repository.MemoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class EditViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : MemoRepository
    var counter : MutableLiveData<Int> = MutableLiveData()

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

//    val textwatcher : ObservableField<String> = ObservableField()

    val titlewatcher = ObservableField("0 / 12")

    fun titleTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        Log.w("tag", "onTextChanged $before")
        titlewatcher.set(s.toString().length.toString() + " / 12")
    }

    val contentwatcher = ObservableField("0 / 50")

    fun contentTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        Log.w("tag", "onTextChanged $s")
        contentwatcher.set(s.toString().length.toString() + " / 50")
    }

}
