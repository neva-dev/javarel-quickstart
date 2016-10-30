package com.neva.javarel.app.adm.content

import com.neva.javarel.framework.api.rest.Controller
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/content/post")
class PostController : Controller() {

    @GET
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    fun getCreate(): Any {
        val ds = repoAdmin.repository().dataStore

        val elmer = Employee("Elmer Fudd", 50000.0)
        ds.save(elmer)

        val daffy = Employee("Daffy Duck", 40000.0)
        ds.save(daffy)

        val pepe = Employee("Pepé Le Pew", 25000.0)
        ds.save(pepe)

        elmer.directReports!!.add(daffy)
        elmer.directReports!!.add(pepe)

        ds.save(elmer)

        return elmer
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    fun getList(): Any {
        val ds = repoAdmin.repository().dataStore
        val query = ds.createQuery(Employee::class.java)
        val employees = query.asList()

        return employees
    }

}