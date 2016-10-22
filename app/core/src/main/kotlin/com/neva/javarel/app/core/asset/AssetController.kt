package com.neva.javarel.app.core.asset

import com.neva.javarel.communication.rest.api.Route
import com.neva.javarel.presentation.asset.api.Asset
import com.neva.javarel.framework.api.rest.Controller
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.core.Response

@Path("/asset")
class AssetController : Controller() {

    @GET
    @Path("/{path:.+}")
    @Route(name = "asset")
    fun getOrigin(@PathParam("path") path: String): Response {
        val asset = resolveAsset(path)

        return Response.ok(asset.read()).type(asset.mimeType).build()
    }

    @GET
    @Path("/compiled/{path:.+}")
    @Route(name = "asset.compiled")
    fun getCompiled(@PathParam("path") path: String): Response {
        val asset = resolveAsset(path)

        return Response.ok(asset.compile()).type(asset.mimeType).build()
    }

    private fun resolveAsset(path: String): Asset {
        return resourceResolver.findOrFail(path).adaptTo(Asset::class)
    }

}
