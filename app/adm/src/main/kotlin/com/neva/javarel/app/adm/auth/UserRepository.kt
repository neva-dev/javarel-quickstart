package com.neva.javarel.app.adm.auth

import com.neva.javarel.storage.database.api.repository.DomainRepository
import org.apache.commons.lang3.RandomStringUtils
import java.util.*
import javax.persistence.EntityManager
import javax.persistence.NoResultException
import javax.persistence.NonUniqueResultException
import kotlin.reflect.KClass

class UserRepository(em: EntityManager) : DomainRepository<UserEntity, Long>(em) {

    override val domainClass: KClass<UserEntity>
        get() = UserEntity::class

    fun register(input: UserInput): UserEntity {
        val user = UserEntity()

        user.email = input.email
        user.password = input.password
        user.name = input.name
        user.nick = input.nick

        user.birth = Date()
        user.salt = RandomStringUtils.randomAscii(64)

        save(user)

        return user
    }

    fun findByCredentials(credentials: UserCredentials): UserEntity? {
        try {
            return createQuery({ builder, criteria, root ->
                criteria.where(builder.or(
                        builder.equal(root.get<Any>(UserEntity.EMAIL_COLUMN), credentials.principal),
                        builder.equal(root.get<Any>(UserEntity.NAME_COLUMN), credentials.principal)
                ))
                criteria.where(builder.equal(root.get<Any>(UserEntity.PASSWORD_COLUMN), credentials.password))

                return@createQuery em.createQuery(criteria)
            }).singleResult
        } catch (e: NoResultException) {
            return null
        } catch (e: NonUniqueResultException) {
            return null
        }
    }

    fun findByPrincipal(principal: String): UserEntity? {
        return findOneBy(mapOf(UserEntity.NAME_COLUMN to principal))
    }

}