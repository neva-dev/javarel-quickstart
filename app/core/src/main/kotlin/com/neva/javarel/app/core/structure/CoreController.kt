package com.neva.javarel.app.core.structure

import com.neva.javarel.communication.rest.api.Redirect
import com.neva.javarel.communication.rest.api.Route
import com.neva.javarel.foundation.api.injection.Osgi
import com.neva.javarel.framework.api.rest.Controller
import com.neva.javarel.framework.api.structure.App
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.core.Response

@Path("/")
class CoreController : Controller() {

    @Osgi
    private lateinit var app: App

    @Path("/")
    @GET
    @Route(names = arrayOf("home", "core.home"))
    fun getHome(): Response {
        val route = app.modules.map { it.options.getOrElse("route", { "" }) as String }.first(String::isNotBlank)

        return Redirect.to(urlGenerator.name(route))
    }

}