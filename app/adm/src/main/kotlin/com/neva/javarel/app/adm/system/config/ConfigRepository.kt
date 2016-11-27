package com.neva.javarel.app.adm.system.config

import com.neva.javarel.storage.database.api.repository.DomainRepository
import javax.persistence.EntityManager
import kotlin.reflect.KClass

class ConfigRepository(em: EntityManager) : DomainRepository<ConfigEntity, Long>(em) {

    override val domainClass: KClass<ConfigEntity>
        get() = ConfigEntity::class

    fun findOneByPid(pid : String) : ConfigEntity? {
        return findOneBy(mapOf(ConfigEntity.PID_COLUMN to pid))
    }

}