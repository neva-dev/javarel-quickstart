package com.neva.javarel.app.adm.dev

import com.neva.javarel.communication.rest.api.RestRouter
import com.neva.javarel.communication.rest.api.Uses
import com.neva.javarel.foundation.api.adapting.AdaptingManager
import com.neva.javarel.integration.api.rest.Controller
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/adm/dev")
class DevController : Controller() {

    @Uses
    private lateinit var router: RestRouter

    @Uses
    private lateinit var adaptingManager: AdaptingManager

    @Path("/rest-routes")
    @GET
    @Produces(MediaType.TEXT_HTML)
    fun getRestRoutes(): String {
        return view("bundle://adm/view/dev/rest-routes.peb")
                .with("routes", router.routes.sortedBy { it.path })
                .render()
    }

    @Path("/adapters")
    @GET
    @Produces(MediaType.TEXT_HTML)
    fun getAdapters(): String {
        return view("bundle://adm/view/dev/adapters.peb")
                .with("adapters", adaptingManager.adapters)
                .render()
    }

}