package com.example.wharareyouupto2.ui.view.activity

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.wharareyouupto2.R
import com.example.wharareyouupto2.alarm.*
import com.example.wharareyouupto2.data.model.Memo
import com.example.wharareyouupto2.databinding.ActivityToDoAddBinding
import com.example.wharareyouupto2.ui.viewmodel.EditViewModel
import java.util.*

class ToDoAddActivity : AppCompatActivity() {

    // 액티비티에서 인터페이스를 받아옴
    private val binding: ActivityToDoAddBinding by lazy {
        ActivityToDoAddBinding.inflate(layoutInflater)
    }
    private val EditViewModel: EditViewModel by viewModels()

    private val cal = Calendar.getInstance()
    private var minhour = cal.get(Calendar.HOUR_OF_DAY)
    private var maxhour = cal.get(Calendar.HOUR_OF_DAY)
    private var minminute = cal.get(Calendar.MINUTE)
    private var maxminute = cal.get(Calendar.MINUTE)
    private var image = 0
    private var alarm = false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        createNotificationChannel()

        binding.editViewModel = EditViewModel

        val year = intent.getIntExtra("year",-1)
        val month = intent.getIntExtra("month",-1)
        val day = intent.getIntExtra("day",-1)

        binding.checkbox.setImageResource(R.drawable.checkboxpick)
        image = R.drawable.checkboxpick

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

        //미사용은 언다바(_)처리
        binding.alarm.setOnCheckedChangeListener { _, isChecked ->
            alarm = isChecked
        }


        binding.minimumtime.text = String.format(Locale.KOREA, "%02d:%02d",minhour,minminute)
        binding.maximumtime.text = String.format(Locale.KOREA, "%02d:%02d",maxhour,maxminute)

        Toast.makeText(this, "추가", Toast.LENGTH_SHORT).show()

        binding.fab.setOnClickListener {

            val title = binding.title.text.toString()
            val content = binding.content.text.toString()

            if (title.isEmpty()){

                Toast.makeText(this, "일정 이름을 입력해주세요.", Toast.LENGTH_SHORT).show()

            } else if(minhour>maxhour || (minhour==maxhour&&minminute>maxminute)){

                Toast.makeText(this, "시간을 제대로 입력해주세요.", Toast.LENGTH_SHORT).show()

            } else {

                val memo = Memo(0, false, title, content, image, alarm, minhour, maxhour, minminute, maxminute, year, month, day)
                EditViewModel.addMemo(memo)
                Toast.makeText(this, "추가", Toast.LENGTH_SHORT).show()
                finish()

            }
            val calendar = Calendar.getInstance()
            calendar.set(year, month, day, minhour, minminute)

            val intent = Intent(applicationContext, AlarmReceiver::class.java)
            intent.putExtra(titleExtra, title)
            intent.putExtra(messageExtra, content)
            intent.putExtra(imageExtra, image)

            val pendingIntent = PendingIntent.getBroadcast(
                applicationContext,
                notificationID,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )

        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val name = "Notify Channel"
        val desc = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
