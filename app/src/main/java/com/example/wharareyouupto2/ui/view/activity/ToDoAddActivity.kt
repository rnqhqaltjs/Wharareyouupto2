package com.example.wharareyouupto2.ui.view.activity

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.wharareyouupto2.R
import com.example.wharareyouupto2.data.model.Memo
import com.example.wharareyouupto2.databinding.ActivityToDoAddBinding
import com.example.wharareyouupto2.ui.viewmodel.EditViewModel
import com.example.wharareyouupto2.util.*
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
    private val notifyId = System.currentTimeMillis().toInt()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.editViewModel = EditViewModel

        //툴바 뒤로가기 UI
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val year = intent.getIntExtra("year",-1)
        val month = intent.getIntExtra("month",-1)
        val day = intent.getIntExtra("day",-1)

        createNotificationsChannel()

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

        binding.minimumtime.text = String.format(Locale.KOREA, "%02d:%02d",minhour,minminute)
        binding.maximumtime.text = String.format(Locale.KOREA, "%02d:%02d",maxhour,maxminute)

        binding.minimumtime.setOnClickListener {

            getMinimumTime()

        }

        binding.maximumtime.setOnClickListener {

            getMaximumTime()

        }

        //미사용은 언다바(_)처리
        binding.alarm.setOnCheckedChangeListener { _, isChecked ->
            alarm = isChecked
            Toast.makeText(this,"${year}년 ${month}월 ${day}일 ${minhour}시 ${minminute}분\n 알람이 " +
                    "울립니다.", Toast.LENGTH_SHORT).show()
        }

        binding.fab.setOnClickListener {

            val title = binding.title.text.toString()
            val content = binding.content.text.toString()

            if (title.isEmpty()){

                Toast.makeText(this, "일정 이름을 입력해주세요.", Toast.LENGTH_SHORT).show()

            } else if(minhour>maxhour || (minhour==maxhour&&minminute>maxminute)){

                Toast.makeText(this, "시간을 제대로 입력해주세요.", Toast.LENGTH_SHORT).show()

            } else {

                val memo = Memo(0, false, title, content, image, alarm, minhour, maxhour, minminute, maxminute, year, month, day, notifyId)
                EditViewModel.addMemo(memo)
                if (alarm && getDate(year,month,day,minhour,minminute) >= System.currentTimeMillis()) {
                    scheduleNotification(image,title,content,year,month,day,minhour,minminute,notifyId)
                }
                Toast.makeText(this, "추가 완료", Toast.LENGTH_SHORT).show()
                finish()

            }

        }

    }

    private fun getMinimumTime(){

        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
            minhour = selectedHour
            minminute = selectedMinute

            binding.minimumtime.text = String.format(Locale.KOREA, "%02d:%02d",minhour,minminute)
        }

        TimePickerDialog(this, timeSetListener, minhour, minminute, true).show()

    }

    private fun getMaximumTime(){

        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
            maxhour = selectedHour
            maxminute = selectedMinute

            binding.maximumtime.text = String.format(Locale.KOREA, "%02d:%02d",maxhour,maxminute)
        }

        TimePickerDialog(this, timeSetListener, maxhour, maxminute, true).show()

    }

    // We create a Notifications channel and register it to our system. We must do this before post our Notifications.
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationsChannel() {
        val name = "Notification Channel"
        val desc = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance)
        channel.description = desc

        // Registering the channel with the system
        val notificationManger = applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManger.createNotificationChannel(channel)
    }

    private fun scheduleNotification(image: Int, title: String, content: String,
                                     year: Int, month: Int, day: Int, hour: Int, minute: Int, notifyId: Int) {

        val intent = Intent(applicationContext, Notifications::class.java).apply {
            putExtra(IMAGE_EXTRA, image)
            putExtra(TITLE_EXTRA, title)
            putExtra(MESSAGE_EXTRA, content)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notifyId,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            getDate(year,month,day,hour,minute),
            pendingIntent
        )

    }

    private fun getDate(year: Int, month: Int, day: Int, hour: Int, minute: Int): Long {
        val cal = Calendar.getInstance()
        cal.set(year,month,day,hour,minute)
        return cal.timeInMillis
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
