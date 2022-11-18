package com.example.fcul_cm_onhand.screens.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import com.example.fcul_cm_onhand.R

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        findViewById<LinearLayout>(R.id.login_button).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}