package com.example.fcul_cm_onhand.model.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import com.example.fcul_cm_onhand.R
import com.example.fcul_cm_onhand.screens.fragments.PopupFragment
import com.example.fcul_cm_onhand.screens.fragments.care_giver.UserFragment

class UsersAdapter(
    private val activity: FragmentActivity,
    private val users: List<UserReceiver>)
    : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var userName: TextView
        val userEmail: TextView

        init {
            // Define click listener for the ViewHolder's View
            userName = itemView.findViewById(R.id.user_name)
            userEmail = itemView.findViewById(R.id.user_email)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_box, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        holder.userName.text = user.name
        holder.userEmail.text = user.email

        holder.itemView.findViewById<RelativeLayout>(R.id.user_button).setOnClickListener {
            val bundle = Bundle().apply {
                putString("name", holder.userName.text.toString())
                putString("email", holder.userEmail.text.toString())
            }
            activity.supportFragmentManager.commit {
                setReorderingAllowed(true)
                val userFragment = UserFragment().apply {
                    arguments = bundle
                }
                add(R.id.fragmentHome, userFragment)
            }
        }
    }

    override fun getItemCount(): Int = users.size


}
