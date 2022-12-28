package com.example.fcul_cm_onhand.model

class User {
    lateinit var name: String
    lateinit var username: String
    lateinit var password: String
    lateinit var type: String

    constructor() {
        // Construtor vazio é necessário para que o objeto possa ser deserializado pelo Firebase
    }

    constructor(name: String, username: String, password: String, type: String) {
        this.name = name
        this.username = username
        this.password = password
        this.type = type
    }

    fun toMap(): Map<String, String> {
        return mapOf(
            "name" to name,
            "username" to username,
            "password" to password,
            "type" to type
        )
    }
}