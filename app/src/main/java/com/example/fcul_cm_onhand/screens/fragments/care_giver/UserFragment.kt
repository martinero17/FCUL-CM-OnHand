package com.example.fcul_cm_onhand.screens.fragments.care_giver

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.fcul_cm_onhand.R
import com.example.fcul_cm_onhand.screens.activities.main.MainActivityViewModel

class UserFragment : Fragment(R.layout.fragment_user) {

    private lateinit var name: String
    private lateinit var email: String

    private val viewModel: MainActivityViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { bundle ->
            name = bundle.getString("name").toString()
            email = bundle.getString("email").toString()
        }


        view.findViewById<TextView>(R.id.user_name_user).text = name
        view.findViewById<TextView>(R.id.user_email_user).text = email

        view.findViewById<Button>(R.id.view_location).setOnClickListener {

            viewModel.downloadLocation()
            viewModel.location.observe(requireActivity()) {
                //val bundle = Bundle().apply { putParcelable("location", it) }
                //val locationFragment = LocationFragment().apply { arguments = bundle }
                requireActivity().supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    add<LocationFragment>(R.id.fragmentHome)
                }
            }
        }
    }
}