package com.eldarwallet.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.eldarwallet.databinding.ActivityMainBinding
import com.eldarwallet.domain.usecases.UserUseCase
import com.eldarwallet.ui.home.HomeActivity
import com.eldarwallet.ui.login.LogInActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var userUseCase: UserUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*//To force app to only light mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)*/

        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))

        setContentView(binding.root)

        checkCurrentSession()
    }

    private fun checkCurrentSession() {
        if(userUseCase.checkCurrentSession()) {
            startActivity(Intent(this, HomeActivity::class.java))
        }else{
            startActivity(Intent(this, LogInActivity::class.java))
        }
        finish()
    }
}