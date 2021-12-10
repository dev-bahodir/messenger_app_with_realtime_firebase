package dev.bahodir.realtimefirebaseentrance.models

class Group {
    var groupName: String? = null
    var groupInfo: String? = null
    var key: String? = null
    var groupImage: Int? = null

    constructor(groupName: String?, groupInfo: String?, key: String, groupImage: Int) {
        this.groupName = groupName
        this.groupInfo = groupInfo
        this.key = key
        this.groupImage = groupImage
    }

    constructor()
}