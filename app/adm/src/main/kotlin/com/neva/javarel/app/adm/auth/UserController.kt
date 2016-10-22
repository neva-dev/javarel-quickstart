package com.neva.javarel.app.adm.auth

import com.neva.javarel.communication.rest.api.Redirect
import com.neva.javarel.framework.api.rest.Controller
import com.neva.javarel.security.auth.api.Credentials
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/adm/auth/user")
class UserController : Controller() {

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    fun getList(): List<UserEntity> {
        return db.session { em ->
            val repo = UserRepository(em)
            val users = repo.findAll()

            return@session users.toList()
        }
    }

    @GET
    @Path("/login")
    @Produces(MediaType.TEXT_HTML)
    fun getLogin(): String {
        return view("bundle://adm/view/auth/user/login.peb").render()
    }

    @POST
    @Path("/login")
    fun postLogin(): Response {
        if (guard.attempt(Credentials())) {
            return Redirect.to(urlGenerator.name("home"))
        }

        return Response.status(Response.Status.UNAUTHORIZED).build()
    }

    @GET
    @Path("/register")
    @Produces(MediaType.TEXT_HTML)
    fun getRegister(): String {
        return view("bundle://adm/view/auth/user/register.peb").render()
    }

    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    fun postRegister(@BeanParam input : UserInput): UserEntity {
        return db.session { em ->
            val repo = UserRepository(em)
            val user = repo.register(input)

            return@session user
        }
    }

}