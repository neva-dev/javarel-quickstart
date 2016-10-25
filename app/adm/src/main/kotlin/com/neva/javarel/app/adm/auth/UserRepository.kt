package com.neva.javarel.app.adm.auth

import com.neva.javarel.storage.database.api.repository.DomainRepository
import org.apache.commons.lang3.RandomStringUtils
import java.util.*
import javax.persistence.EntityManager
import kotlin.reflect.KClass

class UserRepository(em: EntityManager) : DomainRepository<UserEntity, Long>(em) {

    override val domainClass: KClass<UserEntity>
        get() = UserEntity::class

    fun register(input: UserInput): UserEntity {
        val user = UserEntity()

        user.email = input.email
        user.password = input.password
        user.name = input.name

        user.birth = Date()
        user.salt = RandomStringUtils.randomAscii(64)

        save(user)

        return user
    }

}