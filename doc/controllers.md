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

    @Path("/javarel")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getVersion(): Any {
        return mapOf(
                "version" to JavarelConstants.VERSION
        )
    }

## Binders

Whenever your controller is being accessed, we want to use some services.

1. Non-request scoped services can be just standard OSGi services.
2. Request scoped services must be provided by binder factories.


    @Binder
    class GuardProvider : AbstractBinder<Guard>() {

        @Uses
        private lateinit var auth: AuthenticableProvider

        @Context
        private lateinit var request: HttpServletRequest

        override fun configure() {
            bindPerRequest(Guard::class)
        }

        override fun provide(): Guard {
            return RequestGuard(request, auth)
        }

    }

Both types of services can be injected in the same way, simply by using `@Uses` annotation.


    class SomeController {

        @Uses
        protected lateinit var resourceResolver: ResourceResolver

        @Uses
        protected lateinit var guard: Guard

    }


## Providers

Use JAX-RS `@Provider` annotation to extend REST application. To cover e.g exception handling, just write above.

    @Provider
    class ThrowableMapper : ExceptionMapper<Throwable> {
    
        override fun toResponse(e: Throwable): Response {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity("Error occurred! ${e.message}")
                            .type(MediaType.TEXT_PLAIN)
                            .build()
        }
        
    }

In the same way JSON and XML serialization is being registered. See [explanation](http://stackoverflow.com/a/13557596).

## Servlet API

If you really need to use old servlet API to register your endpoint you can use [whiteboard pattern](http://felix.apache.org/documentation/subprojects/apache-felix-http-service.html). 
Just register new services under correct interface.

Servlet:

    @Service(Servlet::class)
    @Component(immediate = true)
    @Properties(
            Property(name = "alias", value = "/bin/sample")
    )
    class SampleServlet : HttpServlet() {

        override fun doGet(request: HttpServletRequest, respone: HttpServletResponse) {
            response.writer.write("Hello World!")
        }

    }
    
**Warning!** Be sure that your alias begins with one of prefixes defined in OSGi configuration named "Javarel REST Configuration". 
Default convention is to register 3rd party servlets under '/bin' prefix.
    
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
    
        override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain?) {
            // ...
        }
        
        override fun destroy() {
            // nothing to do
        }
        
    }

