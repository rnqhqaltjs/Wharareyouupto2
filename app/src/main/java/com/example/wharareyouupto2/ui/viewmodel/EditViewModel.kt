package com.example.wharareyouupto2.ui.viewmodel

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.os.Looper
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
import com.example.wharareyouupto2.util.CalendarDecorator.*
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


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

    //calendar
    fun alarm(memoDatabase: MemoDatabase) {
        val alarms = ArrayList<Long>()

        viewModelScope.launch(Dispatchers.IO) {
            val alarmList = memoDatabase.memoDao().getAll()
            if (alarmList.isNotEmpty()) {
                for (i in alarmList.indices) {
                    val alarm_year = alarmList[i].year
                    val alarm_month = alarmList[i].month
                    val alarm_day = alarmList[i].day
                    val alarm_hour = alarmList[i].minhour
                    val alarm_minute = alarmList[i].minminute

                    Calendar.getInstance().add(alarm_year,alarm_month,alarm_day,alarm_hour,alarm_minute)
                }
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({

            if (dates.size > 0) {
                calendar.addDecorator(EventDecorator(Color.BLACK, dates)) // 점 찍기
            }
        }, 0)

    }

}
