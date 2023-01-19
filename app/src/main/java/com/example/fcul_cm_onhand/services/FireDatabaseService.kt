package com.example.fcul_cm_onhand.services

import android.util.Log
import com.example.fcul_cm_onhand.model.users.User
import com.example.fcul_cm_onhand.model.users.UserGiver
import com.example.fcul_cm_onhand.model.users.UserReceiver
import com.google.firebase.firestore.FirebaseFirestore

import kotlinx.coroutines.tasks.await

const val USER_COLLECTION = "Users"

class FireDatabaseService(private val database: FirebaseFirestore = FirebaseFirestore.getInstance()) {

    suspend fun signUp(name: String, email: String, type: String, token: String){
        val user = User(name, email, type, token)
        if (user.isReceiver())
            database.collection(USER_COLLECTION).document(email).set(UserReceiver(name, email, type, token, "", false))
        else
            database.collection(USER_COLLECTION).document(email).set(UserGiver(name, email, type, token,
                mutableListOf()
            ))
    }

    suspend fun getUserByToken(token: String): User {
        val snapshot = database.collection(USER_COLLECTION).whereEqualTo("token", token).get().await()
        return snapshot.documents.first().toObject(User::class.java)!!
    }

    suspend fun getUserGiverByToken(token: String): UserGiver {
        val snapshot = database.collection(USER_COLLECTION).whereEqualTo("token", token).get().await()
        return snapshot.documents.first().toObject(UserGiver::class.java)!!
    }

    suspend fun getUserByEmail(email: String): User {
        val snapshot = database.collection(USER_COLLECTION).whereEqualTo("email", email).get().await()
        return snapshot.documents.first().toObject(User::class.java)!!
    }

    private suspend fun getUserGiverByEmail(email: String): UserGiver {
        val snapshot = database.collection(USER_COLLECTION).whereEqualTo("email", email).get().await()
        return snapshot.documents.first().toObject(UserGiver::class.java)!!
    }

    private suspend fun getUserReceiverByEmail(email: String): UserReceiver {
        val snapshot = database.collection(USER_COLLECTION).whereEqualTo("email", email).get().await()
        return snapshot.documents.first().toObject(UserReceiver::class.java)!!
    }

    suspend fun addUserToGiver(giverEmail: String, receiverEmail: String) {
        val giver = getUserGiverByEmail(giverEmail)
        val receiver = getUserReceiverByEmail(receiverEmail)

        giver.receivers.add(receiver)

        database.collection(USER_COLLECTION).document(giverEmail).set(giver)
    }

    suspend fun getGiverUsers(email: String): MutableList<UserReceiver> {
        val giver = getUserGiverByEmail(email)

        return giver.receivers
    }
}