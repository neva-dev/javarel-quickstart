package com.neva.javarel.app.adm.post

import com.fasterxml.jackson.annotation.JsonIgnore
import org.bson.types.ObjectId
import org.mongodb.morphia.annotations.Entity
import org.mongodb.morphia.annotations.Id
import java.util.*

@Entity("adm.post")
class PostEntity {

    @JsonIgnore
    @Id
    private lateinit var _id: ObjectId

    val id: String
        get() = _id.toHexString()

    lateinit var title: String

    lateinit var content: String

    @JsonIgnore
    var attachmentId: ObjectId? = null

    var attachmentPath: String? = null

    val createdAt = Date()

}