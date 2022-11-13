package com.example.wharareyouupto2.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wharareyouupto2.R
import com.example.wharareyouupto2.databinding.ActivityOssLicensesMenuBinding

class OssLicensesMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOssLicensesMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOssLicensesMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}