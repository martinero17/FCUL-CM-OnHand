package com.example.fcul_cm_onhand.screens.activities.main

import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.fcul_cm_onhand.R
import com.example.fcul_cm_onhand.model.UserType
import com.example.fcul_cm_onhand.screens.fragments.SettingsFragment
import com.example.fcul_cm_onhand.screens.fragments.care_giver.CareGiverHomeFragment
import com.example.fcul_cm_onhand.screens.fragments.care_giver.NotificationsFragment
import com.example.fcul_cm_onhand.screens.fragments.care_receiver.CareReceiverHomeFragment
import com.example.fcul_cm_onhand.screens.fragments.care_receiver.MedicineFragment
import com.google.android.material.navigation.NavigationBarView


class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userTypeExtra = intent.extras?.getSerializable("UserType", UserType::class.java)
        viewModel.userType = userTypeExtra ?: viewModel.userType

        if (viewModel.userType == UserType.CARE_GIVER) {
            setContentView(R.layout.activity_main_care_giver)

            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<CareGiverHomeFragment>(R.id.fragmentHome)
            }
            setOnItemSelectedListener(findViewById(R.id.bottom_navigation))
        } else if (viewModel.userType == UserType.CARE_RECEIVER) {
            setContentView(R.layout.activity_main_care_receiver)

            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<CareReceiverHomeFragment>(R.id.fragmentHome)
            }
            setOnItemSelectedListener(findViewById(R.id.bottom_navigation))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (viewModel.userType == UserType.CARE_GIVER) {
            menuInflater.inflate(R.menu.bottom_navigation_menu_care_giver, menu)
        } else if (viewModel.userType == UserType.CARE_RECEIVER) {
            menu?.clear()
            menuInflater.inflate(R.menu.bottom_navigation_menu_care_receiver, menu)
        }

        return true
    }

    private fun setOnItemSelectedListener(navigationBarView: NavigationBarView) {
        if (viewModel.userType == UserType.CARE_GIVER) {
            careGiverOnItemSelectedListener(navigationBarView)
        } else if (viewModel.userType == UserType.CARE_RECEIVER) {
            careReceiverOnItemSelectedListener(navigationBarView)
        }
    }

    private fun careGiverOnItemSelectedListener(navigationBarView: NavigationBarView) {
        navigationBarView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home_button -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<CareGiverHomeFragment>(R.id.fragmentHome) //TODO Swap
                    }
                    true
                }
                R.id.notification_button-> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<NotificationsFragment>(R.id.fragmentHome)
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

    private fun careReceiverOnItemSelectedListener(navigationBarView: NavigationBarView) {
        navigationBarView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home_button -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<CareReceiverHomeFragment>(R.id.fragmentHome) //TODO Swap
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