package com.example.fcul_cm_onhand.screens.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import com.example.fcul_cm_onhand.R
import com.example.fcul_cm_onhand.model.UserType
import com.example.fcul_cm_onhand.screens.activities.main.MainActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        FirebaseApp.initializeApp(applicationContext);
        // FirebaseFirestore.getInstance()

        findViewById<LinearLayout>(R.id.register_button).setOnClickListener {
            //startActivity(Intent(this, RegisterActivity::class.java)) //TODO: Send to register activity
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("UserType", UserType.CARE_GIVER)
            startActivity(intent)
            finish()
        }

        findViewById<Button>(R.id.login_button).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("UserType", UserType.CARE_RECEIVER)
            startActivity(intent)
            finish()
        }
    }
}