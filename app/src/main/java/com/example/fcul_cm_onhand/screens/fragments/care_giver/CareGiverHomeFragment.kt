package com.example.fcul_cm_onhand.screens.fragments.care_giver

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fcul_cm_onhand.R
import com.example.fcul_cm_onhand.model.users.UsersAdapter
import com.example.fcul_cm_onhand.screens.activities.login.LoginViewModel
import com.example.fcul_cm_onhand.screens.fragments.PopupFragment
import com.example.fcul_cm_onhand.services.PopupViewModel

class CareGiverHomeFragment : Fragment(R.layout.fragment_home_care_giver) {

    private lateinit var usersRecyclerView: RecyclerView
    private lateinit var usersAdapter: UsersAdapter
    private val viewModel: PopupViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLoggedUser()

        viewModel.user.observe(viewLifecycleOwner) {
            viewModel.getGiverUsers()
            Log.v("USER_INFO", it.email)
            viewModel.receivers.observe(viewLifecycleOwner) { receivers ->
                if (receivers.isNotEmpty()) {
                    Log.v("USER_INFO", receivers.first().email)
                    usersRecyclerView = view.findViewById(R.id.users_recycler_view)
                    usersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                    usersAdapter = UsersAdapter(requireActivity(), receivers)
                    usersRecyclerView.adapter = usersAdapter
                }
            }
        }

        view.findViewById<ImageView>(R.id.add_users_button).setOnClickListener {
           requireActivity().supportFragmentManager.commit {
               setReorderingAllowed(true)
               add<PopupFragment>(R.id.fragmentHome)
           }
        }
    }
}