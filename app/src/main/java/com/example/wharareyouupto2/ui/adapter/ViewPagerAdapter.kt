package com.example.wharareyouupto2.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.wharareyouupto2.ui.view.fragment.DoneListFragment
import com.example.wharareyouupto2.ui.view.fragment.ToDoCalendarFragment
import com.example.wharareyouupto2.ui.view.fragment.ToDoListFragment

class ViewPagerAdapter (fragment : FragmentActivity) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ToDoListFragment()
            1 -> ToDoCalendarFragment()
            else -> DoneListFragment()
        }
    }
}