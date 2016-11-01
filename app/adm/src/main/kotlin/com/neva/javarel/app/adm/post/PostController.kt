package com.neva.javarel.app.adm.post

import com.neva.javarel.framework.api.rest.Controller
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/content/post")
class PostController : Controller() {

    private val repo: PostRepository by lazy {
        PostRepository(repoAdmin.repository(), urlGenerator)
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

    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    fun deleteDelete(@QueryParam("id") id : String): Any {
        val post = repo.find(id)

        if (post == null) {
            return Response.notModified().entity(post).build()
        } else {
            repo.delete(post)

            return Response.ok().entity(post).build()
        }
    }

}