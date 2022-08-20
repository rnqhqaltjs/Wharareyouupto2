package com.example.wharareyouupto2.ui.view.activity

import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.wharareyouupto2.databinding.ActivityToDoEditBinding
import com.example.wharareyouupto2.data.model.Memo
import com.example.wharareyouupto2.ui.viewmodel.EditViewModel
import java.text.SimpleDateFormat
import java.util.*

class ToDoEditActivity : AppCompatActivity() {

    // 액티비티에서 인터페이스를 받아옴
    private val binding: ActivityToDoEditBinding by lazy {
        ActivityToDoEditBinding.inflate(layoutInflater)
    }
    private val EditViewModel: EditViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val type = intent.getStringExtra("type")
        val id = intent.getIntExtra("id",-1)
        val year = intent.getIntExtra("year",-1)
        val month = intent.getIntExtra("month",-1)
        val day = intent.getIntExtra("day",-1)

        binding.minimumtime.setOnClickListener {
            getMinimumTime(this)
        }

        if(type.equals("ADD")){

            binding.minimumtime.text = SimpleDateFormat("HH:mm",Locale.KOREA).format(System.currentTimeMillis())
            binding.maximumtime.text = SimpleDateFormat("HH:mm",Locale.KOREA).format(System.currentTimeMillis()+ 3600000)

            Toast.makeText(this, "추가", Toast.LENGTH_SHORT).show()

        } else{

            val title = intent.getStringExtra("title")
            binding.title.setText(title)

            Toast.makeText(this, "수정", Toast.LENGTH_SHORT).show()

        }

        binding.fab.setOnClickListener {

            val title = binding.title.text.toString()

            if(type.equals("ADD")) {

                if (title.isEmpty()){

                    Toast.makeText(this, "일정 이름을 입력해주세요.", Toast.LENGTH_SHORT).show()

                } else {

                    val memo = Memo(0, false, title, year, month, day)
                    EditViewModel.addMemo(memo)
                    Toast.makeText(this, "추가", Toast.LENGTH_SHORT).show()
                    finish()

                }


            } else {

                if (title.isEmpty()){

                    Toast.makeText(this, "일정 이름을 입력해주세요.", Toast.LENGTH_SHORT).show()

                } else {

                    val memo = Memo(id, false, title, year, month, day)
                    EditViewModel.updateMemo(memo)
                    Toast.makeText(this, "수정", Toast.LENGTH_SHORT).show()
                    finish()

                }

            }

        }

    }

    fun getMinimumTime(context: Context){

        val cal = Calendar.getInstance()

        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)

            binding.minimumtime.text = SimpleDateFormat("HH:mm", Locale.KOREA).format(cal.time)
            binding.maximumtime.text = SimpleDateFormat("HH:mm", Locale.KOREA).format(cal.time)
        }

        TimePickerDialog(context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show()

    }

    fun getMaximumTime(context: Context){

        val cal = Calendar.getInstance()

        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)

            binding.maximumtime.text = SimpleDateFormat("hh:mm a", Locale.KOREA).format(cal.time)
        }

        TimePickerDialog(context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY)+1, cal.get(Calendar.MINUTE), false).show()

    }
}