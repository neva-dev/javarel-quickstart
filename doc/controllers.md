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

Now, get closer into automatic serialization. Let's create a simple controller which will serve Javarel version as JSON at path '/javarel'.

```
@Path("/javarel")
@GET
@Produces(MediaType.APPLICATION_JSON)
fun getVersion(): Any {
    return mapOf(
            "version" to JavarelConstants.VERSION
    )
}
```

## Binders

Whenever your controller is being accessed, we want to use some services.

1. Non-request scoped services can be just standard OSGi services. Annotate field with `@Osgi`.
2. Request scoped services must be bound manually. Annotate field with `@Inject` or sometimes by `@Context` (for injecting e.g `HttpHeaders`). For more details see [Jersey docs](https://jersey.java.net/documentation/latest/jaxrs-resources.html).

```
class SomeController {

    @Osgi
    protected lateinit var resourceResolver: ResourceResolver
    
    @Inject
    protected lateinit var guard: Guard

}
```

To bind request-scoped service to an interface, extend `AbstractBinder` and annotate that class with `@Binder` to get it automatically registered.

```
@Binder
class AuthBinder : AbstractBinder() {

    override fun configure() {
        bindRequestScoped(SessionGuard::class, Guard::class)
        bindRequestScoped(RequestSession::class, Session::class)
    }

}
```

## Providers

Use JAX-RS `@Provider` annotation to extend REST application. To cover e.g exception handling, extend fail-safe implementation of mapper by above:

```
@Provider
class ExceptionMapper : ThrowableExceptionMapper() {

    @Osgi
    @Optional
    private var resolver: ResourceResolver? = null

    override fun map(causeException: Throwable) : Response {
        when (causeException) {
            is NotFoundException,
            is ResourceNotFoundException -> {
                // handle 404
            }
            else -> {
                // handle error by sending e-mail, logging etc
            }
        }
    }
```

In the same way JSON and XML serialization is being registered. See [explanation](http://stackoverflow.com/a/13557596).

## Servlet API

If you really need to use old servlet API to register your endpoint you can use [whiteboard pattern](http://felix.apache.org/documentation/subprojects/apache-felix-http-service.html). 
Just register new services under correct interface.

### Servlet

```
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
```
    
**Warning!** Be sure that your alias begins with one of prefixes defined in OSGi configuration named "Javarel REST Configuration". 
Default convention is to register 3rd party servlets under '/bin' prefix.
    
### Filter
  
```
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
```
