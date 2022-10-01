package com.example.wharareyouupto2.ui.view.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
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
    private var currentYear = calendar.get(Calendar.YEAR)
    private var currentMonth = calendar.get(Calendar.MONTH)
    private var currentDate = calendar.get(Calendar.DATE)

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

        binding.dateFormatted.setOnClickListener {

            val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                currentYear = year
                currentMonth = month
                currentDate = dayOfMonth

                binding.dateFormatted.text = "${year}년 ${month+1}월 ${dayOfMonth}일"

                memoViewModel.readDateData(currentYear,currentMonth,currentDate)
                progressbar()

            }
            DatePickerDialog(requireContext(), dateSetListener, currentYear,currentMonth,currentDate).show()

        }


        // 메모 데이터가 수정되었을 경우 날짜 데이터를 불러옴 (currentData 변경)
        memoViewModel.readAllData.observe(viewLifecycleOwner) {
            memoViewModel.readDateData(currentYear,currentMonth,currentDate)
            progressbar()
        }

        // 현재 날짜 데이터 리스트(currentData) 관찰하여 변경시 어댑터에 전달해줌
        memoViewModel.currentData.observe(viewLifecycleOwner) {
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

            val progress = memodatabase.memoDao().getCompletion(currentYear,currentMonth,currentDate).size
            val max = memodatabase.memoDao().getTodayAll(currentYear,currentMonth,currentDate).size

            binding.progressBar.max = max
            binding.progressBar.progress = progress

            withContext(Dispatchers.Main) {

                binding.progress.text = String.format("%.0f",(progress.toDouble()/max.toDouble())*100) + "%"

                if(binding.progress.text == "NaN%"){
                    binding.progress.text = "0%"
                }

            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}