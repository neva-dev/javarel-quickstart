package com.neva.javarel.app.adm.auth

import com.neva.javarel.storage.api.repository.DomainRepository
import java.util.*
import javax.persistence.EntityManager
import kotlin.reflect.KClass

class UserRepository(em: EntityManager) : DomainRepository<User, Long>(em) {

    override val domainClass: KClass<User>
        get() = User::class

    fun register(email: String, password: String, name: String): User {
        val user = User(email, password, name, Date())

        save(user)

        return user
    }

}