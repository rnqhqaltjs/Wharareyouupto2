package com.example.wharareyouupto2.ui.view.activity

import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.wharareyouupto2.R
import com.example.wharareyouupto2.databinding.ActivityMainBinding
import com.example.wharareyouupto2.ui.adapter.ViewPagerAdapter
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 페이저에 어댑터 연결
        binding.pager.adapter = ViewPagerAdapter(this)

        // 슬라이드하여 페이지가 변경되면 바텀네비게이션의 탭도 그 페이지로 활성화
        binding.pager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.navView.menu.getItem(position).isChecked = true
                }
            }
        )

        // 리스너 연결
        binding.navView.setOnItemSelectedListener(this)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.todolist -> {
                // ViewPager의 현재 item에 첫 번째 화면을 대입
                binding.pager.currentItem = 0
                return true
            }
            R.id.todocalendar -> {
                // ViewPager의 현재 item에 두 번째 화면을 대입
                binding.pager.currentItem = 1
                return true
            }
            R.id.donelist -> {
                // ViewPager의 현재 item에 세 번째 화면을 대입
                binding.pager.currentItem = 2
                return true
            }
            else -> {
                return false
            }
        }
    }

}