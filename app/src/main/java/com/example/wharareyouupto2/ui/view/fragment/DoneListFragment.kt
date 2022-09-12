package com.example.wharareyouupto2.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wharareyouupto2.data.model.Memo
import com.example.wharareyouupto2.databinding.FragmentDonelistBinding
import com.example.wharareyouupto2.ui.adapter.TodoAdapter
import com.example.wharareyouupto2.ui.viewmodel.MemoViewModel

class DoneListFragment : Fragment() {

    private var _binding: FragmentDonelistBinding? = null
    private val binding get() = _binding!!
    private val memoList : List<Memo> = listOf()
    private val adapter : TodoAdapter by lazy { TodoAdapter(requireContext(),memoList,memoViewModel) } // 어댑터 선언
    private val memoViewModel: MemoViewModel by viewModels() // 뷰모델 연결

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDonelistBinding.inflate(inflater, container, false)
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

        memoViewModel.readDoneData.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}