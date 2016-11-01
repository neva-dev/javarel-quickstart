package com.neva.javarel.app.adm.post

import org.glassfish.jersey.media.multipart.FormDataContentDisposition
import org.glassfish.jersey.media.multipart.FormDataParam
import java.io.InputStream
import javax.ws.rs.FormParam

data class PostInput(
        @FormParam("post.title") val title: String,
        @FormParam("post.content") val content: String,
        @FormDataParam("post.attachment") val attachmentInput: InputStream,
        @FormDataParam("post.attachment") val attachmentDetails: FormDataContentDisposition
)
