package com.example.fcul_cm_onhand.model.users

open class User {

    lateinit var name: String
    lateinit var email: String
    lateinit var type: String
    lateinit var token: String

    constructor() {}

    constructor(name: String, email: String, type: String, token: String) {
        this.name = name
        this.email = email
        this.type = type
        this.token = token
    }

    fun isReceiver(): Boolean {
        return type == "CARE_RECEIVER"
    }
}