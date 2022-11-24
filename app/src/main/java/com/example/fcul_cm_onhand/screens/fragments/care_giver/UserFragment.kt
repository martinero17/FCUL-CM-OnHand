package com.example.fcul_cm_onhand.screens.fragments.care_giver

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.fcul_cm_onhand.R

class UserFragment : Fragment(R.layout.fragment_user) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.view_location).setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<LocationFragment>(R.id.fragmentHome)
            }
        }
    }
}