package com.neva.javarel.app.adm.post

import com.neva.javarel.framework.api.rest.Controller
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/content/post")
class PostController : Controller() {

    private val repo = PostRepository(repoAdmin.repository())

    @GET
    @Path("/create")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    fun getCreate(@BeanParam input: PostInput): Any {
        return repo.create(input)
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    fun getList(): Any {
        return repo.all
    }

}