package com.example.wharareyouupto2.ui.viewmodel

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.*
import com.example.wharareyouupto2.CalendarDecorator.*
import com.example.wharareyouupto2.data.MemoDatabase
import com.example.wharareyouupto2.model.Memo
import com.example.wharareyouupto2.repository.MemoRepository
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate


// 뷰모델은 DB에 직접 접근하지 않아야함. Repository 에서 데이터 통신.
class MemoViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData : LiveData<List<Memo>>
    private val readDoneData : LiveData<List<Memo>>
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

    fun deleteMemo(memo : Memo){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMemo(memo)
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
        // 사전작업1. room에서 단일 일정 데이터 가져와서 표시해주기
        val dates = ArrayList<CalendarDay>()

        viewModelScope.launch(Dispatchers.IO) {
            val scheduleList = memoDatabase.memoDao().getAll()
            if (scheduleList.isNotEmpty()) { // schedule이 있을 때만..
                for (i in scheduleList.indices) {
                    val dot_year = scheduleList[i].year
                    val dot_month = scheduleList[i].month
                    val dot_day = scheduleList[i].day

                    dates.add(CalendarDay(dot_year, dot_month, dot_day))
                }
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            // 점은 처음부터 다시 찍어야 함
            calendar!!.removeDecorators()
            calendar.invalidateDecorators()
            // 토, 일 색칠 + 오늘 날짜 표시
            calendar.addDecorators(BoldDecorator(), SundayDecorator(), SaturdayDecorator(), MySelectorDecorator(context), TodayDecorator(context))
            if (dates.size > 0) {
                calendar.addDecorator(EventDecorator(Color.BLACK, dates)) // 점 찍기
            }
        }, 0)

    }

 }
