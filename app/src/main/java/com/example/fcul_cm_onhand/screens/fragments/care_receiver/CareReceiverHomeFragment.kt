package com.example.fcul_cm_onhand.screens.fragments.care_receiver

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.fcul_cm_onhand.R
import com.example.fcul_cm_onhand.screens.activities.main.MainActivityViewModel
import com.google.android.gms.location.LocationServices

class CareReceiverHomeFragment : Fragment(R.layout.fragment_home_care_receiver) {

    private val viewModel: MainActivityViewModel by activityViewModels()

    //@SuppressLint("MissingPermission")
    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val monitoringButton = view.findViewById<Button>(R.id.monitoring_button)

        monitoringButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_vector_pause, 0, 0, 0)
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
            viewModel.sendAlert("MANUAL")
        }

        val checkInButton = view.findViewById<Button>(R.id.check_in_button)
        checkInButton.setOnClickListener {
            viewModel.sendCheckIn()
        }

        val shareLocationButton = view.findViewById<Button>(R.id.share_location_button)
        shareLocationButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // Request the location permission
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 2)
            }

            val locationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
            locationProviderClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    // Do something with the location
                    viewModel.sendLocation(location)
                }
            }

            Toast.makeText(context, "Location sent!", Toast.LENGTH_SHORT).show()
        }
    }

}