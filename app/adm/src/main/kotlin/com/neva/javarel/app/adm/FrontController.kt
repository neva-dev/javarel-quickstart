package com.neva.javarel.app.adm

import com.neva.javarel.app.adm.auth.UserController
import com.neva.javarel.app.adm.system.SystemController
import com.neva.javarel.communication.rest.api.Redirect
import com.neva.javarel.communication.rest.api.Route
import com.neva.javarel.communication.rest.api.UrlGenerator
import com.neva.javarel.communication.rest.api.Uses
import com.neva.javarel.foundation.api.JavarelConstants
import com.neva.javarel.security.auth.api.Guard
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/")
class FrontController {

    @Uses
    private lateinit var urlGenerator: UrlGenerator

    @Uses
    private lateinit var guard: Guard

    @GET
    @Route(name = "root")
    fun getRoot(): Response {
        return Redirect.to(urlGenerator.action(FrontController::getHome))
    }

    @Path("/adm")
    @GET
    @Route(name = "home")
    fun getHome(): Response {
        if (guard.isAuthenticated) {
            return Redirect.to(urlGenerator.action(SystemController::getDashboard))
        } else {
            return Redirect.to(urlGenerator.action(UserController::getLogin))
        }
    }

    @Path("/javarel")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getVersion(): Any {
        return JavarelConstants.asMap()
    }

}