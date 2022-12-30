package com.example.fcul_cm_onhand.Services

import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

interface IFirestoreService {

}

//data class AlertDTO()

class FirestoreService @Inject constructor(val firestore: FirebaseFirestore) : IFirestoreService {

}