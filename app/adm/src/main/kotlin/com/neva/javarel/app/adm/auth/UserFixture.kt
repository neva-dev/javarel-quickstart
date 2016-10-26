package com.neva.javarel.app.adm.auth

import com.neva.javarel.foundation.api.fixture.Fixture
import com.neva.javarel.security.auth.api.AuthConfig
import com.neva.javarel.storage.database.api.DatabaseAdmin
import org.apache.commons.lang3.RandomStringUtils
import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Reference
import org.apache.felix.scr.annotations.Service

@Component(immediate = true)
@Service(Fixture::class)
class UserFixture : Fixture {

    @Reference
    private lateinit var db : DatabaseAdmin

    @Reference
    private lateinit var authConfig : AuthConfig
    
    override fun order(): Int {
        return 100
    }

    override fun install() {
        db.session {
            val repo = UserRepository(it)

            val guest = repo.findByPrincipal(authConfig.guestPrincipal)
            if (guest == null) {
                val input = UserInput(
                        "guest@javarel.neva.zone",
                        RandomStringUtils.randomAlphanumeric(32),
                        authConfig.guestPrincipal,
                        "Guest User"
                )
                repo.register(input)
            }

            val admin = repo.findByPrincipal(authConfig.adminPrincipal)
            if (admin == null) {
                val input = UserInput(
                        "admin@javarel.neva.zone",
                        "admin",
                        authConfig.adminPrincipal,
                        "Admin User"
                )
                repo.register(input)
            }
        }
    }

    override fun uninstall() {
        // nothing to do
    }
}