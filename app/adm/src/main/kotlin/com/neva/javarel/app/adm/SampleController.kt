package com.neva.javarel.app.adm

import com.neva.javarel.foundation.api.JavarelConstants
import com.neva.javarel.framework.api.rest.Controller
import com.neva.javarel.presentation.view.api.View
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/")
class SampleController : Controller() {

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

    @Path("/handlebars")
    @GET
    @Produces(MediaType.TEXT_HTML)
    fun getHandlebars(): String {
        val resource = resourceResolver.findOrFail("bundle://adm/view/sample/handlebars.hbs")
        val source = resource.input.bufferedReader().use { it.readText() }

        return resource.adaptTo(View::class)
                .with(context)
                .with("source", source)
                .render()
    }

}