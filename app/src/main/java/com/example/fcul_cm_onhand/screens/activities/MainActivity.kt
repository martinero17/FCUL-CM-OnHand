package com.example.fcul_cm_onhand.screens.activities

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.fcul_cm_onhand.R
import com.example.fcul_cm_onhand.screens.fragments.SettingsFragment
import com.example.fcul_cm_onhand.screens.fragments.care_receiver.CareReceiverHomeFragment
import com.example.fcul_cm_onhand.screens.fragments.care_receiver.MedicineFragment
import com.google.android.material.navigation.NavigationBarView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<CareReceiverHomeFragment>(R.id.fragmentHome)
        }
        setOnItemSelectedListener(findViewById(R.id.bottom_navigation))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_navigation_menu, menu)
        return true
    }

    private fun setOnItemSelectedListener(navigationBarView: NavigationBarView) {
        navigationBarView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home_button -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<CareReceiverHomeFragment>(R.id.fragmentHome)
                    }
                    true
                }
                R.id.medicine_button-> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<MedicineFragment>(R.id.fragmentHome)
                    }
                    true
                }
                R.id.settings_button -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<SettingsFragment>(R.id.fragmentHome)
                    }
                    true
                }
                else -> true
            }
        }
    }
}