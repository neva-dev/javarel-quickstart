package com.neva.javarel.app.adm.post

import com.neva.javarel.storage.repository.api.Repository
import com.neva.javarel.storage.repository.api.repository.DomainRepository
import org.bson.types.ObjectId

class PostRepository(base: Repository) : DomainRepository<PostEntity>(base, PostEntity::class) {

    fun create(input: PostInput): PostEntity {
        val post = PostEntity()

        post.title = input.title
        post.content = input.content

        if (!input.attachmentDetails.fileName.isNullOrBlank()) {
            val attachment = base.fileStore.createFile(input.attachmentInput)
            attachment.contentType = input.attachmentDetails.type
            attachment.filename = input.attachmentDetails.fileName
            attachment.save()

            post.attachmentId = attachment.id as ObjectId
        }

        base.dataStore.save(post)

        return post
    }

}