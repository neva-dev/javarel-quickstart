package com.neva.javarel.app.sample.structure

import com.neva.javarel.app.sample.post.PostController
import com.neva.javarel.communication.rest.api.Redirect
import com.neva.javarel.communication.rest.api.Route
import com.neva.javarel.foundation.api.JavarelConstants
import com.neva.javarel.framework.api.rest.Controller
import com.neva.javarel.presentation.view.api.View
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/sample")
class SampleController : Controller() {

    @Path("/")
    @GET
    @Route(names = arrayOf("sample.home"))
    fun getHome(): Any {
        return Redirect.to(urlGenerator.action(PostController::app))
    }

    @Path("/handlebars")
    @GET
    @Produces(MediaType.TEXT_HTML)
    fun getHandlebars(): String {
        val resource = resourceResolver.findOrFail("bundle://sample/view/handlebars.hbs")
        val source = resource.input.bufferedReader().use { it.readText() }

        return resource.adaptTo(View::class)
                .with(context)
                .with("source", source)
                .render()
    }

    @Path("/javarel")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getVersion(): Any {
        return mapOf(
                "productName" to JavarelConstants.PRODUCT_NAME,
                "vendorName" to JavarelConstants.VENDOR_NAME,
                "version" to JavarelConstants.VERSION
        )
    }

}