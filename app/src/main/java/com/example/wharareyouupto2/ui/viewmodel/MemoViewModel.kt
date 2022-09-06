package com.example.wharareyouupto2.ui.viewmodel

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.*
import com.example.wharareyouupto2.util.CalendarDecorator.*
import com.example.wharareyouupto2.data.db.MemoDatabase
import com.example.wharareyouupto2.data.model.Memo
import com.example.wharareyouupto2.data.repository.MemoRepository
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


// 뷰모델은 DB에 직접 접근하지 않아야함. Repository 에서 데이터 통신.
class MemoViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData : LiveData<List<Memo>>
    val readDoneData : LiveData<List<Memo>>
    private val repository : MemoRepository

    // get set
    private var _currentData = MutableLiveData<List<Memo>>()
    val currentData : LiveData<List<Memo>>
        get() = _currentData

    init{

        val memoDao = MemoDatabase.getDatabase(application)!!.memoDao()
        repository = MemoRepository(memoDao)
        readAllData = repository.readAllData.asLiveData()
        readDoneData = repository.readDoneData.asLiveData()

    }

    fun updateMemo(memo : Memo){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateMemo(memo)
        }
    }

    fun readDateData(year : Int, month : Int, day : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val tmp = repository.readDateData(year, month, day)
            _currentData.postValue(tmp)
        }
    }

    //calendar
    fun dotDecorator(context: Context,calendar: MaterialCalendarView?, memoDatabase: MemoDatabase) {
        val dates = ArrayList<CalendarDay>()

        viewModelScope.launch(Dispatchers.IO) {
            val scheduleList = memoDatabase.memoDao().getAll()
            if (scheduleList.isNotEmpty()) {
                for (i in scheduleList.indices) {
                    val dot_year = scheduleList[i].year
                    val dot_month = scheduleList[i].month
                    val dot_day = scheduleList[i].day

                    dates.add(CalendarDay(dot_year, dot_month, dot_day))
                }
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({

            calendar!!.removeDecorators()
            calendar.invalidateDecorators()

            calendar.addDecorators(BoldDecorator(), SundayDecorator(), SaturdayDecorator(), MySelectorDecorator(context), TodayDecorator(context))
            if (dates.size > 0) {
                calendar.addDecorator(EventDecorator(Color.BLACK, dates)) // 점 찍기
            }
        }, 0)

    }

 }
