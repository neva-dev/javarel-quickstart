package com.neva.javarel.app.adm.auth

import com.neva.javarel.security.auth.api.Authenticable
import com.neva.javarel.security.auth.api.AuthenticableProvider
import com.neva.javarel.security.auth.api.Credentials
import com.neva.javarel.storage.api.DatabaseAdmin
import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Reference
import org.apache.felix.scr.annotations.Service

@Service(AuthenticableProvider::class)
@Component(immediate = true)
class UserProvider : AuthenticableProvider {

    @Reference
    private lateinit var db: DatabaseAdmin

    override fun byIdentifier(identifier: String): User? {
        return db.session { UserRepository(it).findOneBy(mapOf(User.EMAIL_COLUMN to identifier)) }
    }

    override fun byCredentials(credentials: Credentials): User? {
        return db.session { UserRepository(it).findOneBy(credentials) }
    }

    override val guest: Authenticable
        get() = byIdentifier(Guest.EMAIL) ?: Guest()

}