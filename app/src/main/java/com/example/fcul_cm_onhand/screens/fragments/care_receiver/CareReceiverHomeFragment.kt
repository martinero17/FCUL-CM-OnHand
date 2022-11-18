package com.example.fcul_cm_onhand.screens.fragments.care_receiver

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.fcul_cm_onhand.R

class CareReceiverHomeFragment : Fragment(R.layout.fragment_home_care_receiver) {

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
    }

}