package com.neva.javarel.app.adm.post

import org.glassfish.jersey.media.multipart.FormDataContentDisposition
import org.glassfish.jersey.media.multipart.FormDataParam
import java.io.InputStream

data class PostInput(
        @FormDataParam("post.title") val title: String,
        @FormDataParam("post.content") val content: String,
        @FormDataParam("post.attachment") val attachmentInput: InputStream,
        @FormDataParam("post.attachment") val attachmentDetails: FormDataContentDisposition
)
