package com.example.wharareyouupto2.ui.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wharareyouupto2.data.model.Memo
import com.example.wharareyouupto2.databinding.FragmentTodolistBinding
import com.example.wharareyouupto2.ui.adapter.TodoAdapter
import com.example.wharareyouupto2.ui.view.activity.ToDoEditActivity
import com.example.wharareyouupto2.ui.viewmodel.MemoViewModel
import com.example.wharareyouupto2.ui.viewmodel.ToDoListViewModel
import java.util.*

class ToDoListFragment : Fragment() {

    private var _binding: FragmentTodolistBinding? = null
    private val binding get() = _binding!!
    private val memoList : List<Memo> = listOf()
    private val adapter : TodoAdapter by lazy { TodoAdapter(requireContext(),memoList,memoViewModel) } // 어댑터 선언
    private val memoViewModel: MemoViewModel by viewModels() // 뷰모델 연결

    private val calendar = Calendar.getInstance()

    private val currentYear = calendar.get(Calendar.YEAR)
    private val currentMonth = calendar.get(Calendar.MONTH)
    private val currentDate = calendar.get(Calendar.DATE)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodolistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 아이템에 아이디를 설정해줌 (깜빡이는 현상방지)
        if (!adapter.hasObservers()) {
            adapter.setHasStableIds(true)
        }

        // 아이템을 가로로 하나씩 보여주고 어댑터 연결
        binding.recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        binding.recyclerView.adapter = adapter

        memoViewModel.readAllData.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

        binding.fab.setOnClickListener {

            onFabClicked()

        }

    }

    // Fab 클릭시 사용되는 함수
    private fun onFabClicked(){
        val intent = Intent(requireContext(), ToDoEditActivity::class.java)
        intent.putExtra("year",currentYear)
        intent.putExtra("month",currentMonth)
        intent.putExtra("day",currentDate)
        intent.putExtra("type","ADD")
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}