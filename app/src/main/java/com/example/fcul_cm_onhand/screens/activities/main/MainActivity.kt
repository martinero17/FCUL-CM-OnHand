package com.example.fcul_cm_onhand.screens.activities.main

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.fcul_cm_onhand.OnHandApplication
import com.example.fcul_cm_onhand.R
import com.example.fcul_cm_onhand.model.UserType
import com.example.fcul_cm_onhand.model.alarms.ExactAlarm
import com.example.fcul_cm_onhand.model.alarms.ExactAlarmType
import com.example.fcul_cm_onhand.model.alarms.IExactAlarms
import com.example.fcul_cm_onhand.model.alarms.convertToAlarmTimeMillis
import com.example.fcul_cm_onhand.screens.activities.login.LoginActivity
import com.example.fcul_cm_onhand.screens.fragments.care_giver.CareGiverHomeFragment
import com.example.fcul_cm_onhand.screens.fragments.care_giver.NotificationsFragment
import com.example.fcul_cm_onhand.screens.fragments.care_receiver.CareReceiverHomeFragment
import com.example.fcul_cm_onhand.screens.fragments.care_receiver.MedicineFragment
import com.example.fcul_cm_onhand.screens.fragments.settings.SettingsFragment
import com.example.fcul_cm_onhand.services.FirebaseAuthService
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val exactAlarms = (application as OnHandApplication).exactAlarms.apply {
            rescheduleAlarm()
        }

        if(FirebaseAuthService().isUserLogged()){
            val userTypeExtra = intent.extras?.getSerializable("UserType", UserType::class.java)
            viewModel.userType = userTypeExtra ?: viewModel.userType

            if (viewModel.userType == UserType.CARE_GIVER) {
                setContentView(R.layout.activity_main_care_giver)

                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    add<CareGiverHomeFragment>(R.id.fragmentHome)
                }
                setOnItemSelectedListener(findViewById(R.id.bottom_navigation))
                careGiverUpdateSubscriptions()
                careGiverNotificationChannelsSetup()
            } else if (viewModel.userType == UserType.CARE_RECEIVER) {
                setContentView(R.layout.activity_main_care_receiver)

                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    add<CareReceiverHomeFragment>(R.id.fragmentHome)
                }
                setOnItemSelectedListener(findViewById(R.id.bottom_navigation))
                careReceiverRepeatingAlarmSetup(exactAlarms)
            }
        } else (
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        )


    }

    private fun careReceiverRepeatingAlarmSetup(exactAlarms: IExactAlarms) {
        val triggerTime = convertToAlarmTimeMillis(17, 28)
        exactAlarms.scheduleExactAlarm(ExactAlarm(triggerTime), ExactAlarmType.CHECK_IN)
    }

    private fun careGiverNotificationChannelsSetup() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            var name = getString(R.string.alert_channel_name)
            var descriptionText = getString(R.string.alert_channel_desc)
            var importance = NotificationManager.IMPORTANCE_HIGH
            val alertChannel = NotificationChannel(getString(R.string.alert_channel_id), name, importance)
            alertChannel.description = descriptionText
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            var notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(alertChannel)

            // Create the NotificationChannel
            name = "CheckIn"
            descriptionText = "CheckIn notifications"
            importance = NotificationManager.IMPORTANCE_HIGH
            val checkInChannel = NotificationChannel("CHECK_IN_CHANNEL", name, importance)
            checkInChannel.description = descriptionText
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(checkInChannel)

            // Create the NotificationChannel
            name = "Location"
            descriptionText = "Location notifications"
            importance = NotificationManager.IMPORTANCE_HIGH
            val locationChannel = NotificationChannel("LOCATION_CHANNEL", name, importance)
            locationChannel.description = descriptionText
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(locationChannel)
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

    private fun careGiverUpdateSubscriptions() {
        viewModel.startSubToAlerts()
        viewModel.startSubToCheckIn()
        viewModel.startSubToLocation()
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