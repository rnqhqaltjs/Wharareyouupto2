package com.example.wharareyouupto2.ui.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wharareyouupto2.adapter.TodoAdapter
import com.example.wharareyouupto2.data.MemoDatabase
import com.example.wharareyouupto2.databinding.FragmentTodocalendarBinding
import com.example.wharareyouupto2.model.Memo
import com.example.wharareyouupto2.ui.view.activity.ToDoEditActivity
import com.example.wharareyouupto2.ui.viewmodel.MemoViewModel
import com.example.wharareyouupto2.ui.viewmodel.ToDoCalendarViewModel

class ToDoCalendarFragment : Fragment() {

    private var _binding: FragmentTodocalendarBinding? = null
    private val binding get() = _binding!!
    private val ToDoCalendarViewModel: ToDoCalendarViewModel by viewModels() // 뷰모델 연결
    private val memoList : List<Memo> = listOf()
    private val adapter : TodoAdapter by lazy { TodoAdapter(requireContext(),memoList,ToDoCalendarViewModel) } // 어댑터 선언
    private lateinit var memodatabase: MemoDatabase

    private var year : Int = 0
    private var month : Int = 0
    private var day : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodocalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        memodatabase = MemoDatabase.getDatabase(requireContext())!!

        // 아이템에 아이디를 설정해줌 (깜빡이는 현상방지)
        adapter.setHasStableIds(true)

        // 아이템을 가로로 하나씩 보여주고 어댑터 연결
        binding.recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        binding.recyclerView.adapter = adapter

        binding.calendarView.setOnDateChangedListener { widget, date, selected ->

            year = date.year
            month = date.month
            day = date.day

            // 해당 날짜 데이터를 불러옴 (currentData 변경)
            ToDoCalendarViewModel.readDateData(year,month,day)
        }

        // 메모 데이터가 수정되었을 경우 날짜 데이터를 불러옴 (currentData 변경)
        ToDoCalendarViewModel.readAllData.observe(viewLifecycleOwner) {
            ToDoCalendarViewModel.readDateData(year, month, day)
            ToDoCalendarViewModel.dotDecorator(requireContext(),binding.calendarView,memodatabase)
        }

        // 현재 날짜 데이터 리스트(currentData) 관찰하여 변경시 어댑터에 전달해줌
        ToDoCalendarViewModel.currentData.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

        binding.fab.setOnClickListener {

            if(year == 0) {
                Toast.makeText(activity, "날짜를 선택해주세요.", Toast.LENGTH_SHORT).show()
            }
            else {
                onFabClicked()
            }

        }

    }

    // Fab 클릭시 사용되는 함수
    private fun onFabClicked(){
        val intent = Intent(requireContext(), ToDoEditActivity::class.java)
        intent.putExtra("year",year)
        intent.putExtra("month",month)
        intent.putExtra("day",day)
        intent.putExtra("type","ADD")
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}