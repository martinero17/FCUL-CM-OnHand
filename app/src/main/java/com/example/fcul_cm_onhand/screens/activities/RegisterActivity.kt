package com.example.fcul_cm_onhand.screens.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isEmpty
import com.example.fcul_cm_onhand.Firebase
import com.example.fcul_cm_onhand.R
import com.example.fcul_cm_onhand.model.UserType
import com.example.fcul_cm_onhand.screens.activities.main.MainActivity
import com.google.firebase.FirebaseApp

class RegisterActivity : AppCompatActivity() {

    private lateinit var userType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val fb = Firebase(applicationContext)

        findViewById<LinearLayout>(R.id.login_button).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        findViewById<Button>(R.id.register_button).setOnClickListener {
            val name = findViewById<EditText>(R.id.name_input).text
            val username = findViewById<EditText>(R.id.username_input).text
            val password = findViewById<EditText>(R.id.password_input).text
            val confirmPassword = findViewById<EditText>(R.id.re_password_input).text
            val type = findViewById<RadioGroup>(R.id.radio_types)

            if(verifyCredentials(name, username, password, confirmPassword, type)){
                fb.addUser(name.toString(), username.toString(), password.toString(),
                    userType)

                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("UserType", UserType.CARE_GIVER) // TODO: REMOVE LATER
                startActivity(intent)
                finish()
            }
        }
    }

    private fun verifyCredentials(name: Editable, username: Editable, password: Editable,
                                  confirmPassword: Editable, type: RadioGroup): Boolean {
        return !inputIsEmpty(name, username, password, confirmPassword, type) &&
                passwordMatch(password, confirmPassword)

    }

    private fun passwordMatch(password: Editable, confirmPassword: Editable): Boolean{
        if(password.toString() != confirmPassword.toString()){
            toastMessage("Password doesn't match!$password e $confirmPassword")
            return false
        }
        return true
    }

    private fun inputIsEmpty(name: Editable, username: Editable, password: Editable,
                             confirmPassword: Editable, type: RadioGroup): Boolean{
        if(name.isEmpty()){
            toastMessage("Please fill name!")
            return true
        } else if(username.isEmpty()){
            toastMessage("Please fill username!")
            return true
        } else if(password.isEmpty()){
            toastMessage("Please fill password!")
            return true
        } else if(confirmPassword.isEmpty()){
            toastMessage("Please confirm password!")
            return true
        } else if(type.checkedRadioButtonId == -1){
            toastMessage("Please select one type!")
            return true
        }
        return false
    }

    private fun toastMessage(text: String){
        Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
    }

    // Response to radio button click event for user type
    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.radio_receiver ->
                    if(checked) {
                        userType = UserType.CARE_RECEIVER.toString()
                    }
                R.id.radio_giver ->
                    if (checked) {
                        userType = UserType.CARE_GIVER.toString()
                    }
            }
        }
    }
}