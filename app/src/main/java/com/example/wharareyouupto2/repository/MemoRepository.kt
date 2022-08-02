package com.example.wharareyouupto2.repository

import com.example.wharareyouupto2.data.MemoDao
import com.example.wharareyouupto2.model.Memo
import kotlinx.coroutines.flow.Flow

// 앱에서 사용하는 데이터와 그 데이터 통신을 하는 역할
class MemoRepository(private val memoDao: MemoDao) {

    val readAllData : Flow<List<Memo>> = memoDao.readAllData()
    val readDoneData : Flow<List<Memo>> = memoDao.readDoneData()

    suspend fun addMemo(memo: Memo){
        memoDao.addMemo(memo)
    }

    suspend fun updateMemo(memo: Memo){
        memoDao.updateMemo(memo)
    }

    suspend fun deleteMemo(memo: Memo){
        memoDao.deleteMemo(memo)
    }

    fun readDateData(year : Int, month : Int, day : Int): List<Memo> {
        return memoDao.readDateData(year, month, day)
    }

}