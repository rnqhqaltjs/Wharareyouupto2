package com.example.wharareyouupto2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wharareyouupto2.databinding.TodoItemBinding
import com.example.wharareyouupto2.model.Memo
import com.example.wharareyouupto2.ui.viewmodel.MemoViewModel


class TodoAdapter(private var memoList:List<Memo>, private val memoViewModel: MemoViewModel) : RecyclerView.Adapter<TodoAdapter.MyViewHolder>() {



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

        fun bind(memo: Memo, memoViewModel: MemoViewModel){

            binding.title.text = memo.title

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