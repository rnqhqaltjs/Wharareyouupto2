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

    private val cal = Calendar.getInstance()
    private var minhour = cal.get(Calendar.HOUR_OF_DAY)
    private var maxhour = cal.get(Calendar.HOUR_OF_DAY)
    private var minminute = cal.get(Calendar.MINUTE)
    private var maxminute = cal.get(Calendar.MINUTE)
    private var eminhour = 0
    private var emaxhour = 0
    private var eminminute = 0
    private var emaxminute = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val type = intent.getStringExtra("type")
        val etitle = intent.getStringExtra("title")
        val econtent = intent.getStringExtra("content")
        val eminhour = intent.getIntExtra("minhour",-1)
        val emaxhour = intent.getIntExtra("maxhour",-1)
        val eminminute = intent.getIntExtra("minminute",-1)
        val emaxminute = intent.getIntExtra("maxminute",-1)
        val year = intent.getIntExtra("year",-1)
        val month = intent.getIntExtra("month",-1)
        val day = intent.getIntExtra("day",-1)


        binding.minimumtime.setOnClickListener {
            if(type.equals("ADD")){

                getMinimumTime(this)

            } else {

                editMinimumTime(this)

            }

        }

        binding.maximumtime.setOnClickListener {
            if(type.equals("ADD")){

                getMaximumTime(this)

            } else {

                editMaximumTime(this)

            }
        }

        if(type.equals("ADD")){

            binding.minimumtime.text = String.format(Locale.KOREA, "%02d:%02d",minhour,minminute)
            binding.maximumtime.text = String.format(Locale.KOREA, "%02d:%02d",maxhour,maxminute)

            Toast.makeText(this, "추가", Toast.LENGTH_SHORT).show()

        } else{

            binding.title.setText(etitle)
            binding.content.setText(econtent)
            binding.minimumtime.text = String.format(Locale.KOREA, "%02d:%02d",eminhour,eminminute)
            binding.maximumtime.text = String.format(Locale.KOREA, "%02d:%02d",emaxhour,emaxminute)

            Toast.makeText(this, "수정", Toast.LENGTH_SHORT).show()

        }

        binding.fab.setOnClickListener {

            val title = binding.title.text.toString()
            val content = binding.content.text.toString()

            if(type.equals("ADD")) {

                if (title.isEmpty()){

                    Toast.makeText(this, "일정 이름을 입력해주세요.", Toast.LENGTH_SHORT).show()

                } else {

                    val memo = Memo(0, false, title, content, minhour, maxhour, minminute, maxminute, year, month, day)
                    EditViewModel.addMemo(memo)
                    Toast.makeText(this, "추가", Toast.LENGTH_SHORT).show()
                    finish()

                }


            } else {

                if (title.isEmpty()){

                    Toast.makeText(this, "일정 이름을 입력해주세요.", Toast.LENGTH_SHORT).show()

                } else {

                    val memo = Memo(0, false, title, content, eminhour, emaxhour, minminute, maxminute, year, month, day)
                    EditViewModel.updateMemo(memo)
                    Toast.makeText(this, "수정", Toast.LENGTH_SHORT).show()
                    finish()

                }

            }

        }

    }

    fun getMinimumTime(context: Context){

        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
            minhour = selectedHour
            minminute = selectedMinute

            binding.minimumtime.text = String.format(Locale.KOREA, "%02d:%02d",minhour,minminute)
//            binding.maximumtime.text = String.format(Locale.KOREA, "%02d:%02d",hour,minute)
        }

        TimePickerDialog(context, timeSetListener, minhour, minminute, true).show()

    }

    fun getMaximumTime(context: Context){

        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
            maxhour = selectedHour
            maxminute = selectedMinute

            binding.maximumtime.text = String.format(Locale.KOREA, "%02d:%02d",maxhour,maxminute)
        }

        TimePickerDialog(context, timeSetListener, maxhour, maxminute, true).show()

    }

    fun editMinimumTime(context: Context){

        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
            eminhour = selectedHour
            eminminute = selectedMinute

            binding.minimumtime.text = String.format(Locale.KOREA, "%02d:%02d",eminhour,eminminute)
//            binding.maximumtime.text = String.format(Locale.KOREA, "%02d:%02d",hour,minute)
        }

        TimePickerDialog(context, timeSetListener, eminhour, eminminute, true).show()

    }

    fun editMaximumTime(context: Context){

        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
            emaxhour = selectedHour
            emaxminute = selectedMinute

            binding.maximumtime.text = String.format(Locale.KOREA, "%02d:%02d",emaxhour,emaxminute)
        }

        TimePickerDialog(context, timeSetListener, emaxhour, emaxminute, true).show()

    }

}