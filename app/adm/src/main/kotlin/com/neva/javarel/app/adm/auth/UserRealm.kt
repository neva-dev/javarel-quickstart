package com.neva.javarel.app.adm.auth

import com.neva.javarel.security.auth.api.Credentials
import com.neva.javarel.security.auth.api.PrincipalCredentials
import com.neva.javarel.security.auth.api.PrincipalPasswordCredentials
import com.neva.javarel.security.auth.api.Realm
import com.neva.javarel.storage.database.api.DatabaseAdmin
import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Reference
import org.apache.felix.scr.annotations.Service

@Service(Realm::class)
@Component(immediate = true)
class UserRealm : Realm {

    @Reference
    private lateinit var db: DatabaseAdmin

    override fun supports(credentials: Credentials): Boolean {
        return (credentials is PrincipalPasswordCredentials) || (credentials is PrincipalCredentials)
    }

    override fun byCredentials(credentials: Credentials): UserEntity? {
        return when (credentials) {
            is PrincipalPasswordCredentials -> db.session { UserRepository(it).findByCredentials(credentials) }
            is PrincipalCredentials -> db.session { UserRepository(it).findByPrincipal(credentials.principal) }
            else -> null
        }
    }

}