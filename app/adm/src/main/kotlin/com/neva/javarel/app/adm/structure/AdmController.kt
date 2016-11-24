package com.neva.javarel.app.adm.structure

import com.neva.javarel.app.adm.auth.UserController
import com.neva.javarel.app.adm.system.SystemController
import com.neva.javarel.communication.rest.api.Redirect
import com.neva.javarel.communication.rest.api.Route
import com.neva.javarel.framework.api.rest.Controller
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.core.Response

@Path("/adm")
class AdmController : Controller() {

    @Path("/")
    @GET
    @Route(names = arrayOf("adm.home"))
    fun getHome(): Response {
        if (guard.check) {
            return Redirect.to(urlGenerator.action(SystemController::getDashboard))
        } else {
            return Redirect.to(urlGenerator.action(UserController::getLogin))
        }
    }

}