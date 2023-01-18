package com.example.fcul_cm_onhand.services

import android.util.Log
import android.widget.Toast
import com.example.fcul_cm_onhand.model.User
import com.google.firebase.firestore.FirebaseFirestore

import kotlinx.coroutines.tasks.await

const val USER_COLLECTION = "Users"

class FireDatabaseService(private val database: FirebaseFirestore = FirebaseFirestore.getInstance()) {

    suspend fun signUp(name: String, email: String, type: String, token: String){
        val user = User(name, email, type, token)

        database.collection(USER_COLLECTION).add(user.toMap()).await()
    }

    suspend fun getUserByEmail(email: String): User {
        val snapshot = database.collection(USER_COLLECTION).whereEqualTo("email", email).get().await()
        return snapshot.documents.first().toObject(User::class.java)!!
    }
}