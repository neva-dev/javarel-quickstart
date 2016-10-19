# Controllers

Javarel introduces JAX-RS support using Jersey implementation. To configure it, we have to simply create various types of REST resources.

## Resources

Simplest example. Let's say hello to world!

    @Path("/")
    class FrontController {
   
        @Path("/hello")
        @GET
        @Produces(MediaType.TEXT_PLAIN)
        fun getHello(): String {
            return "Hello World!"
        }
        
    }

Now, get closer into automatic serialization. Let's create a simple controller which will serve Javarel info at path '/javarel'.

    @Path("/")
    class FrontController {
   
        @Path("/javarel")
        @GET
        @Produces(MediaType.APPLICATION_JSON)
        fun getJavarel(): Any {
            return JavarelConstants.asMap()
        }
        
    }

## Error handlers

Use JAX-RS @Provider annotation to extend REST application. To cover exception handling, just write above.

    @Provider
    class ThrowableMapper : ExceptionMapper<Throwable> {
    
        override fun toResponse(e: Throwable): Response {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity("Error occurred! ${e.message}")
                            .type(MediaType.TEXT_PLAIN)
                            .build()
        }
        
    }


## Servlet API

If you really need to use old servlet API to register your endpoint you can use [whiteboard pattern](http://felix.apache.org/documentation/subprojects/apache-felix-http-service.html). Just register new services under correct interface.

Servlet:

    @Service(Servlet::class)
    @Component(immediate = true)
    @Properties(
            Property(name = "alias", value = "/bin/sample")
    )
    class SampleServlet : HttpServlet() {

        override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
            resp.writer.write("Hello World!")
        }

    }
    
**Warning!** Be sure that your alias begins with one of prefixes defined in OSGi configuration named "Javarel REST Configuration".
    
Filter:
    
    @Service(Filter::class)
    @Component(immediate = true)
    @Properties(
            Property(name = "pattern", value = ".*")
            Property(name = "service.ranking", value = "10")
    )
    class SampleFilter : Filter {
        
        override fun init(filterConfig: FilterConfig?) {
            // nothing to do
        }
    
        override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
            // ...
        }
        
        override fun destroy() {
            // nothing to do
        }
        
    }

