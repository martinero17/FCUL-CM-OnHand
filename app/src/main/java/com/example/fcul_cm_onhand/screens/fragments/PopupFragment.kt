package com.example.fcul_cm_onhand.screens.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.fragment.app.*
import com.example.fcul_cm_onhand.R
import com.example.fcul_cm_onhand.screens.activities.login.LoginViewModel
import com.example.fcul_cm_onhand.screens.fragments.care_giver.CareGiverHomeFragment
import com.example.fcul_cm_onhand.services.FireDatabaseService
import com.example.fcul_cm_onhand.services.FirebaseAuthService
import com.example.fcul_cm_onhand.services.PopupViewModel

class PopupFragment : Fragment(R.layout.fragment_popup) {

    private val viewModel: PopupViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.add_user_btn).setOnClickListener {
            val email = view.findViewById<EditText>(R.id.email).text.toString()
            viewModel.getLoggedUser()
            viewModel.user.observe(viewLifecycleOwner) {
                viewModel.addUserToGiver(it.email, email)
            }
        }

        view.findViewById<Button>(R.id.close_page).setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<CareGiverHomeFragment>(R.id.fragmentHome)
            }
        }

    }
}