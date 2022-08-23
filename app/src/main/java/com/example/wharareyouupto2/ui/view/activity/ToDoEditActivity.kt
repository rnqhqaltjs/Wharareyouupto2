package com.example.wharareyouupto2.ui.view.activity

import android.app.TimePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.wharareyouupto2.R
import com.example.wharareyouupto2.data.model.Memo
import com.example.wharareyouupto2.databinding.ActivityToDoAddBinding
import com.example.wharareyouupto2.ui.viewmodel.EditViewModel
import java.util.*

class ToDoEditActivity : AppCompatActivity() {

    // 액티비티에서 인터페이스를 받아옴
    private val binding: ActivityToDoAddBinding by lazy {
        ActivityToDoAddBinding.inflate(layoutInflater)
    }
    private val EditViewModel: EditViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val id = intent.getIntExtra("id",-1)
        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        var minhour = intent.getIntExtra("minhour",-1)
        var maxhour = intent.getIntExtra("maxhour",-1)
        var minminute = intent.getIntExtra("minminute",-1)
        var maxminute = intent.getIntExtra("maxminute",-1)
        val year = intent.getIntExtra("year",-1)
        val month = intent.getIntExtra("month",-1)
        val day = intent.getIntExtra("day",-1)

        binding.minimumtime.setOnClickListener {

            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
                minhour = selectedHour
                minminute = selectedMinute

                binding.minimumtime.text = String.format(Locale.KOREA, "%02d:%02d",minhour,minminute)
            }

            TimePickerDialog(this, timeSetListener, minhour, minminute, true).show()

        }

        binding.maximumtime.setOnClickListener {

            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
                maxhour = selectedHour
                maxminute = selectedMinute

                binding.maximumtime.text = String.format(Locale.KOREA, "%02d:%02d",maxhour,maxminute)
            }

            TimePickerDialog(this, timeSetListener, maxhour, maxminute, true).show()

        }

        binding.title.setText(title)
        binding.content.setText(content)
        binding.minimumtime.text = String.format(Locale.KOREA, "%02d:%02d",minhour,minminute)
        binding.maximumtime.text = String.format(Locale.KOREA, "%02d:%02d",maxhour,maxminute)

        Toast.makeText(this, "수정", Toast.LENGTH_SHORT).show()

        binding.fab.setOnClickListener {

            val title = binding.title.text.toString()
            val content = binding.content.text.toString()

            if (title.isEmpty()){

                Toast.makeText(this, "일정 이름을 입력해주세요.", Toast.LENGTH_SHORT).show()

            } else {

                val memo = Memo(id, false, title, content, minhour, maxhour, minminute, maxminute, year, month, day)
                EditViewModel.updateMemo(memo)
                Toast.makeText(this, "수정", Toast.LENGTH_SHORT).show()
                finish()

            }

        }

    }

}