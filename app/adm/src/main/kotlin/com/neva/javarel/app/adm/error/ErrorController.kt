package com.neva.javarel.app.adm.error

import com.neva.javarel.communication.rest.api.Uses
import com.neva.javarel.presentation.view.api.View
import com.neva.javarel.resource.api.ResourceNotFoundException
import com.neva.javarel.resource.api.ResourceResolver
import com.neva.javarel.security.auth.api.Guard
import org.apache.commons.lang3.exception.ExceptionUtils
import org.jvnet.hk2.annotations.Optional
import org.slf4j.LoggerFactory
import javax.ws.rs.NotFoundException
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class ErrorController : ExceptionMapper<Throwable> {

    @Uses
    @Optional
    private var resolver: ResourceResolver? = null

    @Uses
    @Optional
    private var guard: Guard? = null

    companion object {
        val LOG = LoggerFactory.getLogger(ErrorController::class.java)
    }

    override fun toResponse(causeException: Throwable): Response {
        try {
            when (causeException) {
                is NotFoundException,
                is ResourceNotFoundException -> {
                    LOG.debug("Resource not found", causeException)

                    return view(causeException, "bundle://adm/view/error/not-found.peb")
                }
                else -> {
                    LOG.error("Request error", causeException)

                    return view(causeException, "bundle://adm/view/error/throwable.peb")
                }
            }
        } catch (internalException: Throwable) {
            LOG.error("Internal error occurred while rendering request error view", internalException)

            return text(causeException)
        }
    }

    private fun view(e: Throwable, uri: String): Response {
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

    private fun text(e: Throwable): Response {
        val text = """${ExceptionUtils.getRootCauseMessage(e)}
${ExceptionUtils.getRootCauseStackTrace(e).joinToString("\n")}"""

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(text)
                .type(MediaType.TEXT_PLAIN)
                .build()
    }
}