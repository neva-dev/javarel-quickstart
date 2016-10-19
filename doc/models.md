# Models

Javarel currently supports JPA for persisting data in database. Supported database systems are: _Derby Embedded_ (default), _MySQL_, _PostreSQL_.

## Database admin

To retrieve or update your data use `DatabaseAdmin` service.

    @Path("/user")
    class UserController {

        @Uses
        private lateinit var db: DatabaseAdmin

        @GET
        @Path("/create")
        @Produces(MediaType.APPLICATION_JSON)
        fun getCreate(): User {
            return db.session { em ->
                val repo = UserRepository(em)
                val user = User("foo.bar@example.com", "qwerty")

                repo.save(user)

                return@session user
            }
        }

        @GET
        @Path("/list")
        @Produces(MediaType.APPLICATION_JSON)
        fun getList(): List<User> {
            return db.session { em ->
                val repo = UserRepository(em)
                val users = repo.findAll()

                return@session users.toList()
            }
        }
    }

Multiple databases can be used in one application. Just define named connections in OSGi configuration (_Javarel Storage - ... Connection_). Then you can write:

    db.session("pgsql" { em ->
        // ...
    }

## Repositories

To simplify CRUD operations while operating on domain model, just extend `DomainRepository` (inspired by Spring Data). Then use commonly used methods: `find`, `findAll`, `save`, `count` or write your owns.

    class UserRepository(em: EntityManager) : DomainRepository<User, Long>(em) {
    
        override val domainClass: KClass<User>
            get() = User::class
    
    }
    