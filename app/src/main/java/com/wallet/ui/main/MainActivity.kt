package com.wallet.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.wallet.domain.usecases.UserUseCase
import com.wallet.ui.home.HomeActivity
import com.wallet.ui.login.LogInActivity
import com.wallet.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var userUseCase: UserUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

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