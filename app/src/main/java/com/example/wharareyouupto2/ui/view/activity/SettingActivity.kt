package com.example.wharareyouupto2.ui.view.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ui.AppBarConfiguration
import com.example.wharareyouupto2.databinding.ActivitySettingBinding
import com.example.wharareyouupto2.util.SettingDataStore
import com.example.wharareyouupto2.util.UIMode
import kotlinx.coroutines.launch

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private lateinit var settingDataStore: SettingDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        //툴바 뒤로가기 UI
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        settingDataStore = SettingDataStore(this)
        observeUIPreferences()
        initView()

    }

    private fun observeUIPreferences() {
        settingDataStore.uiModeFlow.asLiveData().observe(this) { uiMode ->
            setCheckedMode(uiMode)
        }
    }

    private fun setCheckedMode(uiMode: UIMode?) {
        when (uiMode) {
            UIMode.LIGHT -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.applyModeSwitch.isChecked = false
            }
            UIMode.DARK -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.applyModeSwitch.isChecked = true
            }
            else -> {}
        }
    }

    private fun initView() {
        binding.applyModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            lifecycleScope.launch {
                when (isChecked) {
                    true -> settingDataStore.setDarkMode(UIMode.DARK)
                    false -> settingDataStore.setDarkMode(UIMode.LIGHT)
                }
            }
        }
    }

    //툴바 뒤로가기 버튼
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}