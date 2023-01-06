package com.example.fcul_cm_onhand.screens.fragments.care_receiver

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.fcul_cm_onhand.R
import com.example.fcul_cm_onhand.screens.activities.main.MainActivityViewModel
import com.example.fcul_cm_onhand.workers.CheckInWorker
import java.util.concurrent.TimeUnit

class CareReceiverHomeFragment : Fragment(R.layout.fragment_home_care_receiver) {

    private val viewModel: MainActivityViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val monitoringButton = view.findViewById<Button>(R.id.monitoring_button)
        monitoringButton.setOnClickListener {
            if(monitoringButton.text == getString(R.string.stop_monitoring_button)){
                monitoringButton.text = getString(R.string.start_monitoring_button)
                monitoringButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_vector_start, 0, 0, 0)
            } else {
                monitoringButton.text = getString(R.string.stop_monitoring_button)
                monitoringButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_vector_pause, 0, 0, 0)
            }
        }

        val emergencyButton = view.findViewById<Button>(R.id.emergency_button)
        emergencyButton.setOnClickListener {
            viewModel.sendAlert()
        }

        //Work requests
        val saveRequest =
            PeriodicWorkRequestBuilder<CheckInWorker>(
                5, TimeUnit.MINUTES, // repeatInterval (the period cycle)
                1, TimeUnit.MINUTES) // flexInterval
                .build()

        WorkManager
            .getInstance(requireContext())
            .enqueue(saveRequest)
    }

}