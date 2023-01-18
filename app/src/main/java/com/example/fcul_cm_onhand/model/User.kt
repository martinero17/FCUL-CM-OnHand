package com.example.fcul_cm_onhand.model

class User {
    lateinit var name: String
    lateinit var email: String
    lateinit var type: String
    lateinit var token: String

    constructor() {
        // Construtor vazio é necessário para que o objeto possa ser deserializado pelo Firebase
    }

    constructor(name: String, email: String, type: String, token: String) {
        this.name = name
        this.email = email
        this.type = type
        this.token = token
    }

    fun toMap(): Map<String, String> {
        return mapOf(
            "name" to name,
            "email" to email,
            "type" to type,
            "token" to token
        )
    }
}