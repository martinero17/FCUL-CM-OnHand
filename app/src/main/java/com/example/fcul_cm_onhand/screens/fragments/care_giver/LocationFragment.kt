package com.example.fcul_cm_onhand.screens.fragments.care_giver



import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.fcul_cm_onhand.R
import com.example.fcul_cm_onhand.screens.activities.main.MainActivityViewModel
import com.example.fcul_cm_onhand.services.LocationDTO

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class LocationFragment : Fragment(R.layout.fragment_location){

    private val viewModel: MainActivityViewModel by activityViewModels()

    private val callback = OnMapReadyCallback { googleMap ->
        val location = viewModel.location.value ?: LocationDTO(38.73, -9.14)
        val latLng = LatLng(location.lat, location.lng)
        googleMap.addMarker(MarkerOptions().position(latLng).title("Care Receiver"))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}