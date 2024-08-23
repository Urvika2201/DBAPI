package com.example.urvikaprac.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.urvikaprac.R

class SplashActivity : AppCompatActivity() {
    private val DELAY_FOR_OPENING_LANDING_ACTIVITY = 2000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, DELAY_FOR_OPENING_LANDING_ACTIVITY.toLong())
    }
}