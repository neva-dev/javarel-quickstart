package com.neva.javarel.app.adm.post

import org.mongodb.morphia.annotations.Entity
import org.mongodb.morphia.annotations.Id
import java.util.*

@Entity("adm.post")
class PostEntity {

    @Id
    lateinit var id: String

    lateinit var title: String

    lateinit var content: String

    var attachmentId: String? = null

    val createdAt = Date()

    var attachmentPath: String? = null

}