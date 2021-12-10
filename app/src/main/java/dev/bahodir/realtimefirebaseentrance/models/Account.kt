package dev.bahodir.realtimefirebaseentrance.models

import java.io.Serializable

class Account : Serializable {
    var displayName: String? = null
    var email: String? = null
    var uid: String? = null
    var photoUrl: String? = null

    constructor(displayName: String?, email: String?, uid: String?, photoUrl: String?) {
        this.displayName = displayName
        this.email = email
        this.uid = uid
        this.photoUrl = photoUrl
    }

    constructor()

}
