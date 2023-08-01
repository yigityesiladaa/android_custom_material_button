package com.custombutton

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.custombutton.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCustom.setOnClickListener{
            Log.e("Clicked","Clicked to custom button")
        }
    }

}