package com.learning.sharedprefrencesinandroid

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.learning.sharedprefrencesinandroid.MainActivity.Companion.EMAIL
import com.learning.sharedprefrencesinandroid.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initSharedPreferences()
        logoutFeature()
        setData()
    }

    private fun setData() {
        //way to retrieve the data from sharedPreferences
        val email = sharedPreferences.getString(EMAIL, "No email found!!")
        binding.txtWelcome.text = email
    }

    /*
  method to initialize sharedPreferences and editor
  */
    private fun initSharedPreferences() {
        sharedPreferences = getSharedPreferences(MainActivity.PREF_FILE_NAME, MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    private fun logoutFeature() {
        binding.btnLogout.setOnClickListener {
            editor.apply {
                clear()
                apply()
                startActivity(Intent(this@HomeActivity, MainActivity::class.java))
                Toast.makeText(this@HomeActivity, "Logout success", Toast.LENGTH_SHORT).show()
            }
        }
    }
}