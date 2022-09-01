package com.example.wharareyouupto2.ui.viewmodel

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.wharareyouupto2.alarm.*
import com.example.wharareyouupto2.data.db.MemoDatabase
import com.example.wharareyouupto2.data.model.Memo
import com.example.wharareyouupto2.data.repository.MemoRepository
import com.example.wharareyouupto2.util.CalendarDecorator.EventDecorator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class EditViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : MemoRepository

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

    val titlewatcher : ObservableField<String> = ObservableField()

    fun titleTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        Log.w("tag", "onTextChanged $s")
        titlewatcher.set(s.toString().length.toString() + " / 12")
    }

    fun titleOnClick(s: String){
        titlewatcher.set(s.length.toString() + " / 12")
    }

    val contentwatcher : ObservableField<String> = ObservableField()

    fun contentTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        Log.w("tag", "onTextChanged $s")
        contentwatcher.set(s.toString().length.toString() + " / 50")
    }

    fun contentOnClick(s: String){
        contentwatcher.set(s.length.toString() + " / 50")
    }

    fun alarm(){}

}
