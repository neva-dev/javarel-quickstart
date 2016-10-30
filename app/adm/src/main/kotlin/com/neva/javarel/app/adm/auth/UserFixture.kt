package com.neva.javarel.app.adm.auth

import com.neva.javarel.foundation.api.fixture.Fixture
import com.neva.javarel.security.auth.api.AuthConfig
import com.neva.javarel.storage.database.api.DatabaseAdmin
import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Reference
import org.apache.felix.scr.annotations.Service

@Component(immediate = true)
@Service(Fixture::class)
class UserFixture : Fixture {

    @Reference
    private lateinit var dbAdmin: DatabaseAdmin

    @Reference
    private lateinit var authConfig: AuthConfig

    override fun install() {
        dbAdmin.session {
            val repo = UserRepository(it)

            val guest = repo.findByPrincipal(authConfig.guest.principal)
            if (guest == null) {
                val input = UserInput(
                        "guest@javarel.neva.zone",
                        authConfig.guest.password,
                        authConfig.guest.principal,
                        "Guest User"
                )
                repo.register(input)
            }

            val admin = repo.findByPrincipal(authConfig.admin.principal)
            if (admin == null) {
                val input = UserInput(
                        "admin@javarel.neva.zone",
                        "admin",
                        authConfig.admin.password,
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