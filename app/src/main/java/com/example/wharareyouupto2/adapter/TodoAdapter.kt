package com.example.wharareyouupto2.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wharareyouupto2.databinding.TodoItemBinding
import com.example.wharareyouupto2.model.Memo
import com.example.wharareyouupto2.ui.view.activity.ToDoInsideActivity
import com.example.wharareyouupto2.ui.viewmodel.MemoViewModel
import com.example.wharareyouupto2.ui.viewmodel.ToDoCalendarViewModel


class TodoAdapter(val context: Context, private var memoList:List<Memo>, private val ToDoCalendarViewModel: ToDoCalendarViewModel) : RecyclerView.Adapter<TodoAdapter.MyViewHolder>() {

    // 어떤 xml 으로 뷰 홀더를 생성할지 지정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = TodoItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    // 뷰 홀더에 데이터를 바인딩
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(memoList[position],ToDoCalendarViewModel)

    }

    // 뷰 홀더의 개수 리턴
    override fun getItemCount(): Int {
        return memoList.size
    }

    inner class MyViewHolder(private val binding: TodoItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(memo: Memo, ToDoCalendarViewModel: ToDoCalendarViewModel){

            binding.title.text = memo.title

            itemView.setOnClickListener {

                val intent = Intent(context, ToDoInsideActivity::class.java)
                intent.putExtra("title", memo.title)
//                intent.putExtra("content", memo.content)
//                intent.putExtra("image", memo.image)
//                intent.putExtra("mintime", memo.mintime)
//                intent.putExtra("maxtime", memo.maxtime)
                intent.putExtra("id", memo.id)
                intent.putExtra("year", memo.year)
                intent.putExtra("month", memo.month)
                intent.putExtra("day", memo.day)
                context.startActivity(intent)

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