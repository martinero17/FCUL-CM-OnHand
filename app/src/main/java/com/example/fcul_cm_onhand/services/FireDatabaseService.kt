package com.example.fcul_cm_onhand.services

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FireDatabaseService(private val database: FirebaseFirestore = FirebaseFirestore.getInstance()) {

    suspend fun signUp(name: String, email: String, type: String, token: String){
        val user = hashMapOf(
            "name" to name,
            "email" to email,
            "type" to type,
            "token" to token
        )

        database.collection("Users").add(user).await()
    }
}