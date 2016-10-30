package com.neva.javarel.app.adm.auth

import com.neva.javarel.security.auth.api.*
import com.neva.javarel.storage.database.api.DatabaseAdmin
import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Reference
import org.apache.felix.scr.annotations.Service

@Service(Realm::class)
@Component(immediate = true)
class UserRealm : BasicRealm() {

    companion object {
        val PRIORITY = 100
    }

    @Reference
    private lateinit var dbAdmin: DatabaseAdmin

    override val priority: Int
        get() = PRIORITY

    override fun byCredentials(credentials: Credentials): UserEntity? {
        return when (credentials) {
            is PrincipalPasswordCredentials -> dbAdmin.session { UserRepository(it).findByCredentials(credentials) }
            is PrincipalCredentials -> dbAdmin.session { UserRepository(it).findByPrincipal(credentials.principal) }
            else -> null
        }
    }

}