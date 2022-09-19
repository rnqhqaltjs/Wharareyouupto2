package com.example.wharareyouupto2.ui.view.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wharareyouupto2.R
import com.example.wharareyouupto2.data.db.MemoDatabase
import com.example.wharareyouupto2.data.model.Memo
import com.example.wharareyouupto2.databinding.FragmentTodolistBinding
import com.example.wharareyouupto2.ui.adapter.TodoAdapter
import com.example.wharareyouupto2.ui.view.activity.ToDoAddActivity
import com.example.wharareyouupto2.ui.viewmodel.MemoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class ToDoListFragment : Fragment() {

    private var _binding: FragmentTodolistBinding? = null
    private val binding get() = _binding!!
    private val memoList : List<Memo> = listOf()
    private val adapter : TodoAdapter by lazy { TodoAdapter(requireContext(),memoList,memoViewModel) } // 어댑터 선언
    private val memoViewModel: MemoViewModel by viewModels() // 뷰모델 연결
    private lateinit var memodatabase: MemoDatabase

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

        binding.memoViewModel = MemoViewModel(requireActivity().application)

        memodatabase = MemoDatabase.getDatabase(requireContext())!!

        // 아이템에 아이디를 설정해줌 (깜빡이는 현상방지)
        if (!adapter.hasObservers()) {
            adapter.setHasStableIds(true)
        }

        // 아이템을 가로로 하나씩 보여주고 어댑터 연결
        binding.recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        binding.recyclerView.adapter = adapter

        // 메모 데이터가 수정되었을 경우 날짜 데이터를 불러옴 (currentData 변경)
        memoViewModel.readAllData.observe(viewLifecycleOwner) {
            progressbar()
            adapter.setData(it)
        }

        binding.fab.setOnClickListener {

            onFabClicked()

        }

    }

    // Fab 클릭시 사용되는 함수
    private fun onFabClicked(){
        Intent(requireContext(), ToDoAddActivity::class.java).apply{
            putExtra("year",currentYear)
            putExtra("month",currentMonth)
            putExtra("day",currentDate)
            startActivity(this)
        }

    }

    @SuppressLint("SetTextI18n")
    private fun progressbar(){

        lifecycleScope.launch(Dispatchers.IO) {

            val progress = memodatabase.memoDao().getCompletion().size
            val max = memodatabase.memoDao().getAll().size

            binding.progressBar.progress = progress
            binding.progressBar.max = max

            withContext(Dispatchers.Main) {

                binding.progress.text = "진행도 : " + String.format("%.0f",(progress.toDouble()/max.toDouble())*100) + "%"
//
//                if(binding.cbc.text == "NaN%"){
//                    binding.cbc.text = "0%"
//                }

            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}