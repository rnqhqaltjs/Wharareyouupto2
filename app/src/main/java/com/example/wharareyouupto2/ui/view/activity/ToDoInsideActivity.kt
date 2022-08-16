package com.example.wharareyouupto2.ui.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.example.wharareyouupto2.R
import com.example.wharareyouupto2.databinding.ActivityToDoEditBinding
import com.example.wharareyouupto2.databinding.ActivityToDoInsideBinding
import com.example.wharareyouupto2.model.Memo
import com.example.wharareyouupto2.ui.viewmodel.InsideViewModel
import com.example.wharareyouupto2.ui.viewmodel.MemoViewModel

class ToDoInsideActivity : AppCompatActivity() {

    private val binding: ActivityToDoInsideBinding by lazy {
        ActivityToDoInsideBinding.inflate(layoutInflater)
    }
    private val InsideViewModel: InsideViewModel by viewModels() // 뷰모델 연결

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val id = intent.getIntExtra("id",-1)
        val title = intent.getStringExtra("title")
        val year = intent.getIntExtra("year",-1)
        val month = intent.getIntExtra("month",-1)
        val day = intent.getIntExtra("day",-1)

        binding.title.text = title

        binding.deletefab.setOnClickListener {

            InsideViewModel.deleteMemo(Memo(id, false, title!!, year, month, day))
            Toast.makeText(this,"삭제 완료", Toast.LENGTH_SHORT).show()
            finish()

        }

        binding.editfab.setOnClickListener {

            val intent = Intent(this,ToDoEditActivity::class.java)
            intent.putExtra("id",id)
            intent.putExtra("title",title)
//            intent.putExtra("content",binding.content.text)
//            intent.putExtra("image",image)
//            intent.putExtra("mintime",binding.mintime.text)
//            intent.putExtra("maxtime",binding.maxtime.text)
            intent.putExtra("year", year)
            intent.putExtra("month", month)
            intent.putExtra("day", day)

            startActivity(intent)
            finish()

        }

    }
}