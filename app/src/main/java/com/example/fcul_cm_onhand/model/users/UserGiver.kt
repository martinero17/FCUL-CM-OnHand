package com.example.fcul_cm_onhand.model.users

class UserGiver : User {

    lateinit var receivers: MutableList<UserReceiver>

    constructor() {}

    constructor(name: String, email: String, type: String, token: String, receivers: MutableList<UserReceiver>):
            super(name, email, type, token){
                this.receivers = receivers
    }

}