# Views

View is a logical unit for combining data with form of presentation.

## Rendering contents

To render HTML contents from a template in the controller just write above:

```
@Path("/adm/system")
class SystemController : Controller() {

    @GET
    @Path("/home")
    @Produces(MediaType.TEXT_HTML)
    fun getDashboard(): String {
        return view("bundle://adm/view/system/dashboard.peb").render()
    }
    
}
```

## Template engines

Supported template engines are:

* [Pebble](http://www.mitchellbosecke.com/pebble/home) 
* [Handlebars](https://github.com/jknack/handlebars.java)

Template engine is being determined by resource URI extension.

### Pebble

Handles *.peb extension.

TODO Describe how to implement Pebble extensions

### Handlebars

Handles *.hbs extension.

TODO Describe how to implement Pebble extensions