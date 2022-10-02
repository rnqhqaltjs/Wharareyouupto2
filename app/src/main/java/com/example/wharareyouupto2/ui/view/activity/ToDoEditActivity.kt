package com.example.wharareyouupto2.ui.view.activity

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.wharareyouupto2.R
import com.example.wharareyouupto2.data.model.Memo
import com.example.wharareyouupto2.databinding.ActivityToDoEditBinding
import com.example.wharareyouupto2.ui.viewmodel.EditViewModel
import com.example.wharareyouupto2.util.*
import java.util.*


class ToDoEditActivity : AppCompatActivity() {

    // 액티비티에서 인터페이스를 받아옴
    private val binding: ActivityToDoEditBinding by lazy {
        ActivityToDoEditBinding.inflate(layoutInflater)
    }
    private val EditViewModel: EditViewModel by viewModels()

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

        val id = intent.getIntExtra("id",-1)
        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        var image = intent.getIntExtra("image",-1)
        var alarm = intent.getBooleanExtra("alarm",false)
        var minhour = intent.getIntExtra("minhour",-1)
        var maxhour = intent.getIntExtra("maxhour",-1)
        var minminute = intent.getIntExtra("minminute",-1)
        var maxminute = intent.getIntExtra("maxminute",-1)
        val year = intent.getIntExtra("year",-1)
        val month = intent.getIntExtra("month",-1)
        val day = intent.getIntExtra("day",-1)
        val notifyId = intent.getIntExtra("notifyId", -1)

        createNotificationsChannel()

        image = when (image) {
            R.drawable.checkboxpick -> {

                binding.checkbox.setImageResource(R.drawable.checkboxpick)
                R.drawable.checkboxpick

            }
            R.drawable.cakepick -> {

                binding.cake.setImageResource(R.drawable.cakepick)
                R.drawable.cakepick

            }
            R.drawable.bookmarkpick -> {

                binding.bookmark.setImageResource(R.drawable.bookmarkpick)
                R.drawable.bookmarkpick

            }
            else -> {

                binding.star.setImageResource(R.drawable.starpick)
                R.drawable.starpick

            }
        }

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

        //미사용은 언다바(_)처리
        binding.alarm.setOnCheckedChangeListener { _, isChecked ->
            alarm = isChecked
            Toast.makeText(this,"${year}년 ${month}월 ${day}일 ${minhour}시 ${minminute}분\n " +
                    "알람이 울립니다.", Toast.LENGTH_SHORT).show()
        }

        binding.minimumtime.setOnClickListener {

            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
                minhour = selectedHour
                minminute = selectedMinute

                binding.minimumtime.text = String.format(Locale.KOREA, "%02d:%02d",minhour,minminute)
            }

            TimePickerDialog(this, timeSetListener, minhour,minminute, true).show()

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
        binding.alarm.isChecked = alarm == true
        binding.minimumtime.text = String.format(Locale.KOREA, "%02d:%02d",minhour,minminute)
        binding.maximumtime.text = String.format(Locale.KOREA, "%02d:%02d",maxhour,maxminute)

        binding.fab.setOnClickListener {

            val title = binding.title.text.toString()
            val content = binding.content.text.toString()

            if (title.isEmpty()){

                Toast.makeText(this, "일정 이름을 입력해주세요.", Toast.LENGTH_SHORT).show()

            } else if(minhour>maxhour || (minhour==maxhour&&minminute>maxminute)){

                Toast.makeText(this, "시간을 제대로 입력해주세요.", Toast.LENGTH_SHORT).show()

            } else{

                val memo = Memo(id, false, title, content,image, alarm, minhour, maxhour, minminute, maxminute, year, month, day, notifyId)
                EditViewModel.updateMemo(memo)
                if (alarm && getDate(year,month,day,minhour,minminute) >= System.currentTimeMillis()) {
                    updateNotification(image,title,content,year,month,day,minhour,minminute,notifyId)
                }
                Toast.makeText(this, "수정 완료", Toast.LENGTH_SHORT).show()
                finish()

            }

        }

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

    private fun updateNotification(image: Int, title: String, content: String,
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
        alarmManager.setExactAndAllowWhileIdle(
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