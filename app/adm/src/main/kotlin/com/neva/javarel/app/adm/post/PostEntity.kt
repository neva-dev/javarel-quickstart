package com.neva.javarel.app.adm.post

import org.bson.types.ObjectId
import org.mongodb.morphia.annotations.Entity
import org.mongodb.morphia.annotations.Id
import java.util.*

@Entity("posts")
class PostEntity {

    @Id
    lateinit var id: ObjectId

    lateinit var title: String

    lateinit var content: String

    var attachmentId: ObjectId? = null

    val createdAt = Date()

    var attachmentUri: String? = null

}