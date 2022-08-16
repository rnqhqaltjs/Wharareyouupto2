package com.example.wharareyouupto2.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.wharareyouupto2.data.MemoDatabase
import com.example.wharareyouupto2.model.Memo
import com.example.wharareyouupto2.repository.MemoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InsideViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : MemoRepository

    init{

        val memoDao = MemoDatabase.getDatabase(application)!!.memoDao()
        repository = MemoRepository(memoDao)

    }

    fun deleteMemo(memo : Memo){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMemo(memo)
        }
    }

}
