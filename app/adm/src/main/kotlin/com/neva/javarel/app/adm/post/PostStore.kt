package com.neva.javarel.app.adm.post

import com.neva.javarel.communication.rest.api.UrlGenerator
import com.neva.javarel.presentation.asset.api.AssetPath
import com.neva.javarel.storage.store.api.Store
import com.neva.javarel.storage.store.api.StoreFileResource
import com.neva.javarel.storage.store.api.store.DomainStore
import org.bson.types.ObjectId

class PostStore(base: Store, private val urlGenerator: UrlGenerator) : DomainStore<PostEntity>(base, PostEntity::class) {

    fun create(input: PostInput): PostEntity {
        val post = PostEntity()

        post.title = input.title
        post.content = input.content

        if (!input.attachmentDetails.fileName.isNullOrBlank()) {
            val attachment = base.fileStore.createFile(input.attachmentInput)
            attachment.contentType = input.attachmentDetails.type
            attachment.filename = input.attachmentDetails.fileName
            attachment.save()

            post.attachmentId = (attachment.id as ObjectId)
        }

        save(post)

        return post
    }

    override fun init(entity: PostEntity) {
        if (entity.attachmentId != null) {
            entity.attachmentPath = AssetPath(urlGenerator).generate(
                    StoreFileResource.uri(base.connection.name, base.fileStore.bucketName, entity.attachmentId.toString())
            )
        }
    }

}