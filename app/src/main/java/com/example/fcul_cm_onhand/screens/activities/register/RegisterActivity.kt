package com.example.fcul_cm_onhand.screens.activities.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import com.example.fcul_cm_onhand.R
import com.example.fcul_cm_onhand.model.UserType
import com.example.fcul_cm_onhand.screens.activities.login.LoginActivity
import com.example.fcul_cm_onhand.screens.activities.main.MainActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var userType: UserType
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        handleRegister()
        handleLogin()
    }

    private fun handleRegister() {
        findViewById<Button>(R.id.register_button).setOnClickListener {
            val name = findViewById<EditText>(R.id.name_input).text.toString()
            val email = findViewById<EditText>(R.id.email_input).text.toString()
            val password = findViewById<EditText>(R.id.password_input).text.toString()

            viewModel.register(name, email, userType.toString(), password)
        }

        viewModel.registerSuccess.observe(this) {
            val intent = Intent(this, MainActivity::class.java)
                .putExtra("UserType", userType)
            startActivity(intent)
        }

        viewModel.registerError.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleLogin() {
        findViewById<LinearLayout>(R.id.login_button).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
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
                        userType = UserType.CARE_RECEIVER
                    }
                R.id.radio_giver ->
                    if (checked) {
                        userType = UserType.CARE_GIVER
                    }
            }
        }
    }

/*    private fun verifyCredentials(name: Editable, username: Editable, password: Editable,
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
    }*/
}