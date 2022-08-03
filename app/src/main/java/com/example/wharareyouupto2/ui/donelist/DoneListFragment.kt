package com.example.wharareyouupto2.ui.donelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.wharareyouupto2.databinding.FragmentDonelistBinding
import com.example.wharareyouupto2.ui.todocalendar.ToDoCalendarViewModel

class DoneListFragment : Fragment() {

    private var _binding: FragmentDonelistBinding? = null
    private val binding get() = _binding!!

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

        val notificationsViewModel = ViewModelProvider(this)[DoneListViewModel::class.java]

        val textView: TextView = binding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}