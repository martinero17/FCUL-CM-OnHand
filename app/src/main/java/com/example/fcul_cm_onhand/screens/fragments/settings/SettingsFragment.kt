package com.example.fcul_cm_onhand.screens.fragments.settings

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fcul_cm_onhand.R
import com.example.fcul_cm_onhand.screens.activities.main.MainActivity

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    val viewModel: SettingsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.logOut_btn).setOnClickListener {
            viewModel.logOut()
            startActivity(Intent(context, MainActivity::class.java))
        }
    }
}