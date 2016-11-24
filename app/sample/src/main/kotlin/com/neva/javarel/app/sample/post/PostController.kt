package com.neva.javarel.app.sample.post

import com.neva.javarel.framework.api.rest.Controller
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/sample/content/post")
class PostController : Controller() {

    private val store: PostStore by lazy {
        PostStore(storeAdmin.store(), urlGenerator)
    }

    @GET
    @Path("/")
    @Produces(MediaType.TEXT_HTML)
    fun app(): Any {
        return view("bundle://sample/view/post/app.peb")
                .with("posts", store.all)
                .render()
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    fun create(@BeanParam input: PostInput): Any {
        return store.create(input)
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    fun list(): Any {
        return store.all
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun delete(@PathParam("id") id : String): Any {
        val post = store.find(id)

        if (post == null) {
            return Response.notModified().entity(post).build()
        } else {
            store.delete(post)

            return Response.ok().entity(post).build()
        }
    }

}