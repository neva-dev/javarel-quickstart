package com.neva.javarel.app.adm.auth

import com.neva.javarel.security.auth.api.AuthConfig
import com.neva.javarel.security.auth.api.Credentials
import com.neva.javarel.security.auth.api.PrincipalCredentials
import com.neva.javarel.security.auth.api.Realm
import com.neva.javarel.storage.database.api.DatabaseAdmin
import org.apache.commons.lang3.RandomStringUtils
import org.apache.felix.scr.annotations.Activate
import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Reference
import org.apache.felix.scr.annotations.Service
import org.slf4j.LoggerFactory

@Service(Realm::class)
@Component(immediate = true)
class UserRealm : Realm {

    companion object {
        val LOG = LoggerFactory.getLogger(UserRealm::class.java)
    }

    override fun supports(credentials: Credentials): Boolean {
        return (credentials is UserCredentials) || (credentials is PrincipalCredentials)
    }

    @Reference
    private lateinit var db: DatabaseAdmin

    @Reference
    private lateinit var authConfig : AuthConfig

    @Activate
    protected fun activate() {
        createGuestIfNotExists()
    }

    private fun createGuestIfNotExists() {
        db.session {
            val repo = UserRepository(it)
            var guest = repo.findByPrincipal(authConfig.guestPrincipal)
            if (guest == null) {
                val input = UserInput(
                        "guest@neva.zone",
                        RandomStringUtils.randomAlphanumeric(32),
                        authConfig.guestPrincipal,
                        "Guest User"
                )
                guest = repo.register(input)

                LOG.info("Missing guest user created automatically: {}", guest)
            }
        }
    }

    override fun byCredentials(credentials: Credentials): UserEntity? {
        return when (credentials) {
            is UserCredentials -> db.session { UserRepository(it).findByCredentials(credentials) }
            is PrincipalCredentials -> db.session { UserRepository(it).findByPrincipal(credentials.principal) }
            else -> null
        }
    }

}