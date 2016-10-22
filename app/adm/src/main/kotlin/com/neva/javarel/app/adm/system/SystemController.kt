package com.neva.javarel.app.adm.system

import com.neva.javarel.framework.api.rest.Controller
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/adm/system")
class SystemController : Controller() {

    @GET
    @Path("/home")
    @Produces(MediaType.TEXT_HTML)
    fun getDashboard(): String {
        return view("bundle://adm/view/system/dashboard.peb").render()
    }

    @GET
    @Path("/frame")
    @Produces(MediaType.TEXT_HTML)
    fun getFrame(): String {
        return view("bundle://adm/view/system/frame.peb").render()
    }

}