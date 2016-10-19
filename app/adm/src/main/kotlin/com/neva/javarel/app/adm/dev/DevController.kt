package com.neva.javarel.app.adm.dev

import com.neva.javarel.communication.rest.api.Uses
import com.neva.javarel.communication.rest.api.RestRouter
import com.neva.javarel.foundation.api.adapting.AdaptingManager
import com.neva.javarel.presentation.view.api.View
import com.neva.javarel.resource.api.ResourceResolver
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/adm/dev")
class DevController {

    @Uses
    private lateinit var resourceResolver: ResourceResolver

    @Uses
    private lateinit var router: RestRouter

    @Uses
    private lateinit var adaptingManager: AdaptingManager

    @Path("/rest-routes")
    @GET
    @Produces(MediaType.TEXT_HTML)
    fun getRestRoutes(): String {
        return resourceResolver.findOrFail("bundle://adm/view/dev/rest-routes.peb")
                .adaptTo(View::class)
                .with("routes", router.routes.sortedBy { it.path })
                .render()
    }

    @Path("/adapters")
    @GET
    @Produces(MediaType.TEXT_HTML)
    fun getAdapters(): String {
        return resourceResolver.findOrFail("bundle://adm/view/dev/adapters.peb")
                .adaptTo(View::class)
                .with("adapters", adaptingManager.adapters)
                .render()
    }

}