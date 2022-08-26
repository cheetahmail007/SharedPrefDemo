package com.learning.sharedprefrencesinandroid

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.learning.sharedprefrencesinandroid.databinding.ActivitySecurePrefBinding

class SecurePrefActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecurePrefBinding
    private lateinit var encryptedSharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecurePrefBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createSecureSharedPreferences()
        binding.btnSetUserName.setOnClickListener { setUserNameToPref() }
        binding.btnGetUserName.setOnClickListener { getUserNameFromPref() }
    }

    private fun getUserNameFromPref() {
        val username = encryptedSharedPreferences.getString(NAME, "data not found!!")
        Toast.makeText(this, username, Toast.LENGTH_LONG).show()
    }

    private fun setUserNameToPref() {
        val username = encryptedSharedPreferences.getString(NAME, "default value")
        username?.let {
            encryptedSharedPreferences.edit {
                putString(NAME, "Abhishek Pathak")
            }
        }
    }

    private fun createSecureSharedPreferences() {
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        val mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

        encryptedSharedPreferences = EncryptedSharedPreferences.create(
            FILE_NAME,
            mainKeyAlias,
            this,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    companion object {
        const val FILE_NAME = "secure_user_file"
        const val NAME = "name"
    }
}