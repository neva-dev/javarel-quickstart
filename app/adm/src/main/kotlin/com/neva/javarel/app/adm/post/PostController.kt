package com.neva.javarel.app.adm.post

import com.neva.javarel.framework.api.rest.Controller
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/content/post")
class PostController : Controller() {

    private val repo: PostRepository by lazy {
        PostRepository(repoAdmin.repository())
    }

    @GET
    @Path("/")
    @Produces(MediaType.TEXT_HTML)
    fun getApp(): Any {
        return view("bundle://adm/view/post/app.peb")
                .with("posts", repo.all)
                .render()
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    fun postCreate(@BeanParam input: PostInput): Any {
        return repo.create(input)
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    fun getList(): Any {
        return repo.all
    }

}