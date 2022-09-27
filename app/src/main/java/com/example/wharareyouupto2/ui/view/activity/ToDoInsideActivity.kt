package com.example.wharareyouupto2.ui.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.api.load
import com.example.wharareyouupto2.data.model.Memo
import com.example.wharareyouupto2.databinding.ActivityToDoInsideBinding
import com.example.wharareyouupto2.ui.viewmodel.InsideViewModel
import com.google.android.material.snackbar.Snackbar

class ToDoInsideActivity : AppCompatActivity() {

    private val binding: ActivityToDoInsideBinding by lazy {
        ActivityToDoInsideBinding.inflate(layoutInflater)
    }
    private val InsideViewModel: InsideViewModel by viewModels() // 뷰모델 연결

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        //툴바 뒤로가기 UI
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id = intent.getIntExtra("id",-1)
        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        val image = intent.getIntExtra("image",-1)
        val alarm = intent.getBooleanExtra("alarm",false)
        val minhour = intent.getIntExtra("minhour", -1)
        val maxhour = intent.getIntExtra("maxhour", -1)
        val minminute = intent.getIntExtra("minminute", -1)
        val maxminute = intent.getIntExtra("maxminute", -1)
        val year = intent.getIntExtra("year",-1)
        val month = intent.getIntExtra("month",-1)
        val day = intent.getIntExtra("day",-1)
        val notifyId = intent.getIntExtra("notifyId", -1)

        binding.title.text = title
        binding.content.text = content
        binding.image.load(image){ size(200,200) }
        binding.minhour.text = String.format("%02d", minhour)
        binding.maxhour.text = String.format("%02d", maxhour)
        binding.minminute.text = String.format("%02d", minminute)
        binding.maxminute.text = String.format("%02d", maxminute)
        binding.year.text = year.toString() + "년"
        binding.month.text = (month+1).toString() +"월"
        binding.day.text = day.toString()+ "일"

        binding.deletefab.setOnClickListener {

            InsideViewModel.deleteMemo(Memo(id, false, title!!, content, image, alarm, minhour, maxhour, minminute, maxminute, year, month, day, notifyId))
            Snackbar.make(it, "삭제 완료", Snackbar.LENGTH_SHORT).show()
            finish()

        }

        binding.editfab.setOnClickListener {

            Intent(this,ToDoEditActivity::class.java).apply{
                putExtra("id",id)
                putExtra("title",title)
                putExtra("content",content)
                putExtra("image",image)
                putExtra("alarm",alarm)
                putExtra("minhour",minhour)
                putExtra("maxhour",maxhour)
                putExtra("minminute",minminute)
                putExtra("maxminute",maxminute)
                putExtra("year", year)
                putExtra("month", month)
                putExtra("day", day)
                putExtra("notifyId", notifyId)
                startActivity(this)
            }

            finish()

        }

    }

    //툴바 뒤로가기 버튼
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}