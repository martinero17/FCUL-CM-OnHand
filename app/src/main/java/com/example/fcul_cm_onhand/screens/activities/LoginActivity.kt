package com.example.fcul_cm_onhand.screens.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.fcul_cm_onhand.R
import com.example.fcul_cm_onhand.screens.activities.care_receiver.HomeCareReceiverActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<TextView>(R.id.register_text).setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        findViewById<Button>(R.id.login_button).setOnClickListener {
            startActivity(Intent(this, HomeCareReceiverActivity::class.java))
        }
    }
}