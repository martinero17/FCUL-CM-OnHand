package com.example.fcul_cm_onhand.model.users

class UserReceiver: User {

    var location: String = ""
    var inActivity: Boolean = false

    constructor() {}

    constructor(name: String, email: String, type: String, token: String, location: String, inActivity: Boolean):
            super(name, email, type, token){
                this.location = location
                this.inActivity = inActivity
            }

}