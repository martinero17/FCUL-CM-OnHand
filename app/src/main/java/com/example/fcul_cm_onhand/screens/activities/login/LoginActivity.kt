package com.example.fcul_cm_onhand.screens.activities.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import com.example.fcul_cm_onhand.R
import com.example.fcul_cm_onhand.model.UserType
import com.example.fcul_cm_onhand.screens.activities.main.MainActivity
import com.example.fcul_cm_onhand.screens.activities.register.RegisterActivity
import com.example.fcul_cm_onhand.services.FireDatabaseService
import com.example.fcul_cm_onhand.services.FirebaseAuthService
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var mEmail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        handleSignIn()
        handleSignUp()
    }

    private fun handleSignIn() {

        findViewById<Button>(R.id.login_button).setOnClickListener {
            val email = findViewById<EditText>(R.id.email_input).text.toString()
            val pw = findViewById<EditText>(R.id.password_input).text.toString()
            viewModel.login(email, pw)
            mEmail = email
        }

        viewModel.loginSuccess.observe(this) {
            Toast.makeText(this, "You are logged in.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)

            viewModel.getLoggedUser(mEmail)

            viewModel.user.observe(this) {
                val userType: UserType
                if(it.type == "CARE_RECEIVER"){
                    userType = UserType.CARE_RECEIVER
                } else {
                    userType = UserType.CARE_GIVER
                }
                intent.putExtra("UserType", userType)
                startActivity(intent)
            }
        }

        viewModel.loginError.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleSignUp() {
        findViewById<LinearLayout>(R.id.register_button).setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}