package com.example.wharareyouupto2.ui.view.activity

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.example.wharareyouupto2.databinding.ActivityTodoaddBinding
import com.example.wharareyouupto2.model.Memo
import com.example.wharareyouupto2.ui.viewmodel.MemoViewModel

class TodoaddActivity : AppCompatActivity() {

    // 액티비티에서 인터페이스를 받아옴
    private val binding: ActivityTodoaddBinding by lazy {
        ActivityTodoaddBinding.inflate(layoutInflater)
    }
    private val memoViewModel: MemoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val type = intent.getStringExtra("type")

        val year = intent.getIntExtra("year",-1)
        val month = intent.getIntExtra("month",-1)
        val day = intent.getIntExtra("day",-1)

        if(type.equals("ADD")){

            Toast.makeText(this, "추가", Toast.LENGTH_SHORT).show()

        } else{
            Toast.makeText(this, "수정", Toast.LENGTH_SHORT).show()
        }

        binding.fab.setOnClickListener {
            val title = binding.title.text.toString()

            if(type.equals("ADD")) {

                if (title.isEmpty()){

                    Toast.makeText(this, "일정 이름을 입력해주세요.", Toast.LENGTH_SHORT).show()

                } else {

                    val memo = Memo(0, false, title, year, month, day)
                    memoViewModel.addMemo(memo)
                    Toast.makeText(this, "추가", Toast.LENGTH_SHORT).show()
                    finish()

                }


            } else {


            }
        }

    }
}