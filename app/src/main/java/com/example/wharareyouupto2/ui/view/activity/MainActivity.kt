package com.example.wharareyouupto2.ui.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.wharareyouupto2.R
import com.example.wharareyouupto2.data.db.MemoDatabase
import com.example.wharareyouupto2.data.model.Memo
import com.example.wharareyouupto2.databinding.ActivityMainBinding
import com.example.wharareyouupto2.ui.adapter.ViewPagerAdapter
import com.example.wharareyouupto2.ui.viewmodel.InsideViewModel
import com.example.wharareyouupto2.ui.viewmodel.MemoViewModel
import com.google.android.material.navigation.NavigationBarView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val MemoViewModel: MemoViewModel by viewModels() // 뷰모델 연결
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var memodatabase: MemoDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        memodatabase = MemoDatabase.getDatabase(this)!!

        toggle = ActionBarDrawerToggle(this,binding.drawer,
            R.string.drawer_opened,
            R.string.drawer_closed
        )

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toggle.syncState()

        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {

                //데이터 초기화
                R.id.item_deleteall -> {

                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("주의")
                    builder.setMessage("데이터를 초기화 하시겠습니까?")

                    builder.setNegativeButton("아니오") { dialog, which ->

                    }

                    builder.setPositiveButton("네") { dialog, which ->

                        lifecycleScope.launch(Dispatchers.IO) {

                            MemoViewModel.deleteAllMemo(memodatabase)

                        }

                        Toast.makeText(this,"데이터 초기화 완료", Toast.LENGTH_SHORT).show()
                        finish();//인텐트 종료
                        overridePendingTransition(0, 0);//인텐트 효과 없애기
                        val intent = intent; //인텐트
                        startActivity(intent); //액티비티 열기
                        overridePendingTransition(0, 0)

                    }

                    builder.show()

                }

                //나가기
                R.id.item_exit -> {

                    moveTaskToBack(true)
                    finishAndRemoveTask()
                    exitProcess(0)

                }

                //세팅
                R.id.item_setting -> {

                    val intent = Intent(this, SettingActivity::class.java)
                    startActivity(intent)

                }

                //오픈소스 라이선스
                R.id.item_info -> {

                    val intent = Intent(this, OssLicensesMenuActivity::class.java)
                    startActivity(intent)

                }

            }
            true
        }

        // 페이저에 어댑터 연결
        binding.pager.adapter = ViewPagerAdapter(this)

        // 슬라이드하여 페이지가 변경되면 바텀네비게이션의 탭도 그 페이지로 활성화
        binding.pager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.navView.menu.getItem(position).isChecked = true

                    when (position) {
                        0 -> binding.toolbarTitle.text = "할 일 목록"
                        1 -> binding.toolbarTitle.text = "캘린더"
                        2 -> binding.toolbarTitle.text = "완료 목록"

                    }
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
                binding.toolbarTitle.text = "할 일 목록"
                return true
            }
            R.id.todocalendar -> {
                // ViewPager의 현재 item에 두 번째 화면을 대입
                binding.pager.currentItem = 1
                binding.toolbarTitle.text = "캘린더"
                return true
            }
            R.id.donelist -> {
                // ViewPager의 현재 item에 세 번째 화면을 대입
                binding.pager.currentItem = 2
                binding.toolbarTitle.text = "완료 목록"
                return true
            }
            else -> {
                return false
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){

            return true
        }

        return super.onOptionsItemSelected(item)
    }

}