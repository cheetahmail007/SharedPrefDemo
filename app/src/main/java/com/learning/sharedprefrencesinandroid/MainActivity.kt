package com.learning.sharedprefrencesinandroid

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.learning.sharedprefrencesinandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initSharedPreferences()
        initViews()
        checkUserIsLoggedIn()
    }

    private fun initViews() {
        binding.apply {
            btnLogin.setOnClickListener {
                doLogin(edEmail.text.toString(), edPassword.text.toString())
            }
        }
    }

    private fun doLogin(email: String, password: String) {
        editor.apply {
            putString(EMAIL, email)
            putString(PASSWORD, password)
            val isSuccessful = commit()
            if (isSuccessful) {
                Toast.makeText(this@MainActivity, "Login success", Toast.LENGTH_SHORT).show()
                intentToHomeScreen()
            }
        }
    }

    /*
    method to initialize sharedPreferences and editor
    */
    private fun initSharedPreferences() {
        sharedPreferences = getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    private fun checkUserIsLoggedIn() {
        if (sharedPreferences.contains(EMAIL)) {
            intentToHomeScreen()
        }
    }

    private fun intentToHomeScreen() {
        startActivity(Intent(this@MainActivity, HomeActivity::class.java))
    }

    companion object {
        const val PREF_FILE_NAME = "login_details"
        const val EMAIL = "email"
        const val PASSWORD = "password"
    }
}