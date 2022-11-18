package com.example.fcul_cm_onhand.screens.activities.care_receiver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.fcul_cm_onhand.R

class HomeCareReceiverActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_care_receiver)

        val monitoringButton = findViewById<Button>(R.id.monitoring_button)
        monitoringButton.setOnClickListener {
            if(monitoringButton.text == getString(R.string.stop_monitoring_button)){
                monitoringButton.text = getString(R.string.start_monitoring_button)
                monitoringButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_vector_start, 0, 0, 0)
            } else {
                monitoringButton.text = getString(R.string.stop_monitoring_button)
                monitoringButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_vector_pause, 0, 0, 0)
            }
        }
    }
}