package com.example.wharareyouupto2.ui.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.wharareyouupto2.databinding.TodoItemBinding
import com.example.wharareyouupto2.data.model.Memo
import com.example.wharareyouupto2.ui.view.activity.ToDoInsideActivity
import com.example.wharareyouupto2.ui.viewmodel.MemoViewModel


class TodoAdapter(val context: Context, private var memoList:List<Memo>, private val memoViewModel: MemoViewModel) : RecyclerView.Adapter<TodoAdapter.MyViewHolder>() {

    // 어떤 xml 으로 뷰 홀더를 생성할지 지정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = TodoItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    // 뷰 홀더에 데이터를 바인딩
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(memoList[position],memoViewModel)

    }

    // 뷰 홀더의 개수 리턴
    override fun getItemCount(): Int {
        return memoList.size
    }

    inner class MyViewHolder(private val binding: TodoItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(memo: Memo, memoViewModel: MemoViewModel) {

            binding.memo = memo

            binding.title.text = memo.title
            binding.image.load(memo.image){size(100,100)}
            binding.minhour.text = String.format("%02d", memo.minhour)
            binding.maxhour.text = String.format("%02d", memo.maxhour)
            binding.minminute.text = String.format("%02d", memo.minminute)
            binding.maxminute.text = String.format("%02d", memo.maxminute)

            itemView.setOnClickListener {

                Intent(context, ToDoInsideActivity::class.java).apply{
                    putExtra("id", memo.id)
                    putExtra("title", memo.title)
                    putExtra("content", memo.content)
                    putExtra("image", memo.image)
                    putExtra("minhour", memo.minhour)
                    putExtra("maxhour", memo.maxhour)
                    putExtra("minminute", memo.minminute)
                    putExtra("maxminute", memo.maxminute)
                    putExtra("year", memo.year)
                    putExtra("month", memo.month)
                    putExtra("day", memo.day)
                    context.startActivity(this)

                }

            }

            // 체크 리스너 초기화 해줘 중복 오류 방지
            binding.checkbox.setOnCheckedChangeListener(null)

            binding.checkbox.setOnCheckedChangeListener { buttonView, isChecked ->

                if (isChecked) {

                    binding.title.paintFlags = binding.title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    val memo = Memo(memo.id, true, memo.title, memo.content, memo.image, memo.minhour, memo.maxhour, memo.minminute, memo.maxminute, memo.year, memo.month, memo.day)
                    memoViewModel.updateMemo(memo)

                } else {

                    binding.title.paintFlags = binding.title.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    val memo = Memo(memo.id, false, memo.title, memo.content, memo.image, memo.minhour, memo.maxhour, memo.minminute, memo.maxminute, memo.year, memo.month, memo.day)
                    memoViewModel.updateMemo(memo)

                }

            }
        }

    }

    // 메모 리스트 갱신
    fun setData(memo : List<Memo>){
        memoList = memo
        notifyDataSetChanged()
    }

    // 아이템에 아이디를 설정해줌 (깜빡이는 현상방지)
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}