package com.neva.javarel.app.core.asset

import com.neva.javarel.communication.rest.api.Route
import com.neva.javarel.framework.api.rest.Controller
import com.neva.javarel.presentation.asset.api.AssetPath
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.core.Response

@Path("/asset")
class AssetController : Controller() {

    @GET
    @Path("/{" + AssetPath.PATH_PARAM + ":.+}")
    @Route(names = arrayOf(AssetPath.ROUTE_NAME))
    fun getOrigin(@PathParam(AssetPath.PATH_PARAM) path: String): Response {
        val asset = asset(path)

        return Response.ok(asset.read()).type(asset.mimeType).build()
    }

}
