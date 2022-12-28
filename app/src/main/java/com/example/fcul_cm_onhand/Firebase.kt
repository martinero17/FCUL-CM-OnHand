package com.example.fcul_cm_onhand

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.fcul_cm_onhand.model.User
import com.google.firebase.firestore.FirebaseFirestore

class Firebase(context: Context) {
    private val mContext: Context = context
    private var usersRef = FirebaseFirestore.getInstance().collection("users")

    fun addUser(name: String, username: String, password: String, type: String){
        if(isUserValid(username)){
            val user = User(name, username, password, type).toMap()
            usersRef.add(user)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
        } else {
            Toast.makeText(mContext, "Username already exists.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isUserValid(username: String): Boolean{
        val query = usersRef.whereEqualTo("username", username)
        var userValid = true

        query.get()
            .addOnSuccessListener { documents ->
                userValid = documents.isEmpty
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }

        return userValid
    }
}