package com.example.fcul_cm_onhand.screens.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fcul_cm_onhand.R
import com.example.fcul_cm_onhand.screens.activities.care_receiver.HomeCareReceiverActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_care_receiver)

/*        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_button -> {
                    // Respond to navigation home button click
                    startActivity(Intent(applicationContext, HomeCareReceiverActivity::class.java))
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.medicine_button -> {
                    // Respond to navigation medicine button click
                    true
                }
                R.id.settings_button -> {
                    startActivity(Intent(applicationContext, SettingsActivity::class.java))
                    overridePendingTransition(0, 0)
                    true
                }
                else -> false
            }
        }*/
    }

}