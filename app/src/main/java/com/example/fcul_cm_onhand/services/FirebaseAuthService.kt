package com.example.fcul_cm_onhand.services

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class FirebaseAuthService(val auth: FirebaseAuth = FirebaseAuth.getInstance()) {

    suspend fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).await()
    }

    suspend fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    suspend fun getUserToken(): String? {
        return auth.currentUser?.getIdToken(false)?.await()?.token
    }

    suspend fun getUserEmail(): String? {
        return auth.currentUser?.email
    }

    fun isUserLogged(): Boolean {
        return auth.currentUser != null
    }

    fun logOut() {
        auth.signOut()
    }

}