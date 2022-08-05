package com.example.wharareyouupto2.ui.view

import android.graphics.Insets.add
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wharareyouupto2.adapter.TodoAdapter
import com.example.wharareyouupto2.databinding.FragmentTodocalendarBinding
import com.example.wharareyouupto2.model.Memo
import com.example.wharareyouupto2.ui.dialog.MyCustomDialog
import com.example.wharareyouupto2.ui.dialog.MyCustomDialogInterface
import com.example.wharareyouupto2.ui.viewmodel.MemoViewModel

class ToDoCalendarFragment : Fragment(), MyCustomDialogInterface {

    private var _binding: FragmentTodocalendarBinding? = null
    private val binding get() = _binding!!
    private val memoViewModel: MemoViewModel by viewModels() // 뷰모델 연결
    private val adapter : TodoAdapter by lazy { TodoAdapter(memoViewModel) } // 어댑터 선언

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

        // 아이템에 아이디를 설정해줌 (깜빡이는 현상방지)
        adapter.setHasStableIds(true)

        // 아이템을 가로로 하나씩 보여주고 어댑터 연결
        binding.recyclerview.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        binding.recyclerview.adapter = adapter

//        val memoViewModel = ViewModelProvider(this)[MemoViewModel::class.java]

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        binding.calendarview.setOnDateChangedListener { widget, date, selected ->

            this.year = date.year
            this.month = date.month
            this.day = date.day

            // 해당 날짜 데이터를 불러옴 (currentData 변경)
            memoViewModel.readDateData(this.year,this.month,this.day)
        }

        // 현재 날짜 데이터 리스트(currentData) 관찰하여 변경시 어댑터에 전달해줌
        memoViewModel.currentData.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })

        binding.calendarDialogButton.setOnClickListener {

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
        val myCustomDialog = MyCustomDialog(requireActivity(),this)
        myCustomDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onOkButtonClicked(title: String) {
        // 선택된 날짜로 메모를 추가해줌
        val memo = Memo(0,false, title, year, month, day)
        memoViewModel.addMemo(memo)
        Toast.makeText(activity, "추가", Toast.LENGTH_SHORT).show()
    }
}