package com.example.fcul_cm_onhand.screens.fragments.care_giver

import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.example.fcul_cm_onhand.R
import com.example.fcul_cm_onhand.screens.activities.login.LoginViewModel
import com.example.fcul_cm_onhand.screens.fragments.PopupFragment

class CareGiverHomeFragment : Fragment(R.layout.fragment_home_care_giver) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<RelativeLayout>(R.id.user_button).setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<UserFragment>(R.id.fragmentHome)
            }
        }

        view.findViewById<ImageView>(R.id.remove_user).setOnClickListener {
            view.findViewById<RelativeLayout>(R.id.user_button).visibility = INVISIBLE
        }

        view.findViewById<ImageView>(R.id.add_users_button).setOnClickListener {
           requireActivity().supportFragmentManager.commit {
               setReorderingAllowed(true)
               add<PopupFragment>(R.id.fragmentHome)
           }
        }
    }
}