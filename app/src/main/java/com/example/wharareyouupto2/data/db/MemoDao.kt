package com.example.wharareyouupto2.data.db

import androidx.room.*
import com.example.wharareyouupto2.data.model.Memo
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoDao {

    // OnConflictStrategy.IGNORE = 동일한 아이디가 있을 시 무시
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMemo(memo : Memo)

    @Update
    suspend fun updateMemo(memo : Memo)

    @Delete
    suspend fun deleteMemo(memo : Memo)

    // 큰 날짜부터 출력
    @Query("SELECT * FROM Memo ORDER BY minhour , minminute")
    fun readAllData() : Flow<List<Memo>>

    // 날짜 정보를 입력받아 그 날짜에 해당하는 메모만 반환
    @Query("SELECT * FROM Memo WHERE year = :year AND month = :month AND day = :day ORDER BY minhour , minminute")
    fun readDateData(year : Int, month : Int, day : Int) : List<Memo>

    // 완료한 메모만 출력
    @Query("SELECT * FROM Memo WHERE `check` = 1 ORDER BY year DESC, month DESC, day DESC, id DESC")
    fun readDoneData() : Flow<List<Memo>>

    // 캘린더에 모든 날짜 가져옴
    @Query("SELECT * FROM Memo ")
    fun getCalendarAll() : List<Memo>

    // 모든 오늘 날짜를 가져옴
    @Query("SELECT * FROM Memo WHERE year = :year AND month = :month AND day = :day")
    fun getTodayAll(year : Int, month : Int, day : Int) : List<Memo>

    // 모든 오늘 날짜 중에 완료된 날짜만 가져옴
    @Query("SELECT * FROM Memo WHERE `check` = 1 AND year = :year AND month = :month AND day = :day")
    fun getCompletion(year : Int, month : Int, day : Int) : List<Memo>

    //모든 데이터를 제거
    @Query("Delete FROM Memo ")
    fun deleteAllMemo()

}