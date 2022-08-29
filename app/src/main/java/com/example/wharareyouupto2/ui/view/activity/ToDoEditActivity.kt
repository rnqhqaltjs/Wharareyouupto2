package com.example.wharareyouupto2.ui.view.activity

import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.wharareyouupto2.R
import com.example.wharareyouupto2.data.model.Memo
import com.example.wharareyouupto2.databinding.ActivityToDoEditBinding
import com.example.wharareyouupto2.ui.viewmodel.EditViewModel
import java.util.*


class ToDoEditActivity : AppCompatActivity() {

    // 액티비티에서 인터페이스를 받아옴
    private val binding: ActivityToDoEditBinding by lazy {
        ActivityToDoEditBinding.inflate(layoutInflater)
    }
    private val EditViewModel: EditViewModel by viewModels()
    private var image = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.editViewModel = EditViewModel

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

        binding.checkbox.setOnClickListener {

            binding.checkbox.setImageResource(R.drawable.checkboxpick)
            binding.cake.setImageResource(R.drawable.cake)
            binding.bookmark.setImageResource(R.drawable.bookmark)
            binding.star.setImageResource(R.drawable.star)

            image = R.drawable.checkboxpick
        }

        binding.cake.setOnClickListener {

            binding.cake.setImageResource(R.drawable.cakepick)
            binding.checkbox.setImageResource(R.drawable.checkbox)
            binding.bookmark.setImageResource(R.drawable.bookmark)
            binding.star.setImageResource(R.drawable.star)

            image = R.drawable.cakepick
        }

        binding.bookmark.setOnClickListener {

            binding.bookmark.setImageResource(R.drawable.bookmarkpick)
            binding.checkbox.setImageResource(R.drawable.checkbox)
            binding.cake.setImageResource(R.drawable.cake)
            binding.star.setImageResource(R.drawable.star)

            image = R.drawable.bookmarkpick
        }

        binding.star.setOnClickListener {

            binding.star.setImageResource(R.drawable.starpick)
            binding.checkbox.setImageResource(R.drawable.checkbox)
            binding.cake.setImageResource(R.drawable.cake)
            binding.bookmark.setImageResource(R.drawable.bookmark)

            image = R.drawable.starpick
        }

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

            } else if(minhour>maxhour || (minhour==maxhour&&minminute>maxminute)){

                Toast.makeText(this, "시간을 제대로 입력해주세요.", Toast.LENGTH_SHORT).show()

            } else{

                val memo = Memo(id, false, title, content,image, minhour, maxhour, minminute, maxminute, year, month, day)
                EditViewModel.updateMemo(memo)
                Toast.makeText(this, "수정", Toast.LENGTH_SHORT).show()
                finish()

            }

        }

    }

}