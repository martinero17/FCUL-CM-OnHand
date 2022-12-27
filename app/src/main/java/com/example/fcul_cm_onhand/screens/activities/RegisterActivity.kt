package com.example.fcul_cm_onhand.screens.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import com.example.fcul_cm_onhand.R
import com.example.fcul_cm_onhand.model.UserType
import com.example.fcul_cm_onhand.screens.activities.main.MainActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        findViewById<LinearLayout>(R.id.login_button).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        findViewById<Button>(R.id.register_button).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("UserType", UserType.CARE_GIVER)
            startActivity(intent)

            finish()
        }
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.radio_receiver ->
                    if(checked) {
                        // TODO
                    }
                R.id.radio_giver ->
                    if (checked) {
                        // TODO
                    }
            }
        }
    }
}