package com.example.wharareyouupto2.ui.activity

import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wharareyouupto2.R
import com.example.wharareyouupto2.databinding.ActivityTodoaddBinding

class TodoaddActivity : AppCompatActivity() {

    // 액티비티에서 인터페이스를 받아옴
    private val binding: ActivityTodoaddBinding by lazy {
        ActivityTodoaddBinding.inflate(layoutInflater)
    }
//    private var todoaddActivityInterface: TodoaddActivityInterface = myInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.fab
        val memoEditView : EditText = findViewById(R.id.title)

//        // 배경 투명하게 바꿔줌
//        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.fab.setOnClickListener {
            val title = binding.title.toString()

            // 입력하지 않았을 때
            if ( TextUtils.isEmpty(title)){
                Toast.makeText(this, "메모를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }

            // 입력 창이 비어 있지 않을 때
            else{
                // 메모를 추가해줌
//                todoaddActivityInterface.onOkButtonClicked(title)
                finish()
            }
        }
//
//        // 취소 버튼 클릭 시 종료
//        cancelButton.setOnClickListener { dismiss()}
    }
}