package com.rishik.stockapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rishik.stockapp.R
import com.rishik.stockapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}