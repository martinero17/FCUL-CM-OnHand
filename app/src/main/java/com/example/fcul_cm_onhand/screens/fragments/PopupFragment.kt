package com.example.fcul_cm_onhand.screens.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.fcul_cm_onhand.R

class PopupFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.fragment_popup, null))
            builder.setPositiveButton("Add user", DialogInterface.OnClickListener { dialog, id ->
                // TODO: add the user
            })
            builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                dialog?.cancel()
            })
            builder.create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }


}