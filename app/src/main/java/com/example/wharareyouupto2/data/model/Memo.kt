package com.example.wharareyouupto2.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Memo(

    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    val check : Boolean,
    val title : String,
    val content : String?,
    val minhour : Int,
    val maxhour : Int,
    val minminute : Int,
    val maxminute : Int,
    val year : Int,
    val month : Int,
    val day : Int

)