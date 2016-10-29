package com.neva.javarel.app.adm.error

import com.neva.javarel.communication.rest.api.Osgi
import com.neva.javarel.framework.api.rest.ThrowableExceptionMapper
import com.neva.javarel.presentation.view.api.View
import com.neva.javarel.resource.api.ResourceNotFoundException
import com.neva.javarel.resource.api.ResourceResolver
import com.neva.javarel.security.auth.api.Guard
import org.apache.commons.lang3.exception.ExceptionUtils
import org.jvnet.hk2.annotations.Optional
import javax.inject.Inject
import javax.ws.rs.NotFoundException
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.ext.Provider

@Provider
class ExceptionMapper : ThrowableExceptionMapper() {

    @Osgi
    @Optional
    private var resolver: ResourceResolver? = null

    @Inject
    @Optional
    private var guard: Guard? = null

    override fun map(causeException: Throwable) : Response {
        when (causeException) {
            is NotFoundException,
            is ResourceNotFoundException -> {
                LOG.debug("Resource not found", causeException)

                return toView(causeException, "bundle://adm/view/error/not-found.peb")
            }
            else -> {
                LOG.error("Request error", causeException)

                return toView(causeException, "bundle://adm/view/error/exception.peb")
            }
        }
    }

    private fun toView(e: Throwable, uri: String): Response {
        if (resolver == null || guard == null) {
            return text(e)
        }

        val html = resolver!!.findOrFail(uri)
                .adaptTo(View::class)
                .with("guard", guard!!)
                .with("message", ExceptionUtils.getRootCauseMessage(e))
                .with("stackTrace", ExceptionUtils.getRootCauseStackTrace(e).joinToString("\n"))
                .render()

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(html)
                .type(MediaType.TEXT_HTML)
                .build()
    }

}