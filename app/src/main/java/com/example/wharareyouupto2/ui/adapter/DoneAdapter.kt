package com.example.wharareyouupto2.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wharareyouupto2.data.model.Memo
import com.example.wharareyouupto2.databinding.DoneItemBinding
import com.example.wharareyouupto2.ui.viewmodel.MemoViewModel

class DoneAdapter(val context: Context, private var memoList:List<Memo>, private val memoViewModel: MemoViewModel): RecyclerView.Adapter<DoneAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = DoneItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(memoList[position])
    }

    override fun getItemCount(): Int {
        return memoList.size
    }

    inner class MyViewHolder(var binding: DoneItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(memo: Memo) {

            binding.outRecyclerview.adapter = TodoAdapter(context, memoList,memoViewModel)
            binding.outRecyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
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