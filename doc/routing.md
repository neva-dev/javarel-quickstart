# Routing

Javarel organizes path generation basing on routes. Route is combination of REST resource and method which indicates full path. 
Every route has an action in corresponding format: _[class].[method]_. For instance _com.company.example.app.FrontController.getDashboard_. Route could also be named via `@Route` annotation in your controller.

## URL generation

Instead of hardcoding paths in your view templates, you can use `UrlGenerator` service which will generate correct path taking into account possible path change in you controller code.

    @Path("/")
    class FrontController {
    
        @Uses
        private lateinit var urlGenerator: UrlGenerator
    
        @GET
        @Route(name = "root")
        fun getRoot(): Response {
            return Redirect.to(urlGenerator.action(FrontController::getHome))
        }
    
        @Path("/adm")
        @GET
        @Route(name = "home")
        fun getHome(): Response {
            return Redirect.to(urlGenerator.action(SystemController::getDashboard))
        }
    
    }
    

## Action aliasing

Instead of using fully qualified class names in your view templates, you can use alias for prefixing class packages. Just put special header in your bundle manifest (app/build.gradle).

    jar {
        manifest {
            instruction 'Rest-Route-Alias', 'app=com.company.example.app'
        }
    }

Then action _com.company.example.app.FrontController.getDashboard_ can be shortened to _app.FrontController.getDashboard_.

Following ways to generate links in Pebble template will produce same:

    <a href="{{ route(name='home') }}
    <a href="{{ route(action='com.company.example.app.FrontController.getHome') }}
    <a href="{{ route(action='app.FrontController.getHome') }}




