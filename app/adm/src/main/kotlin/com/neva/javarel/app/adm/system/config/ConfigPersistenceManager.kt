package com.neva.javarel.app.adm.system.config

import com.neva.javarel.storage.database.api.DatabaseAdmin
import org.apache.felix.cm.PersistenceManager
import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Reference
import org.apache.felix.scr.annotations.Service
import java.util.*

@Component(immediate = true)
@Service(PersistenceManager::class)
class ConfigPersistenceManager : PersistenceManager {

    @Reference
    private lateinit var dbAdmin: DatabaseAdmin

    @Suppress("UNCHECKED_CAST")
    override fun store(pid: String, properties: Dictionary<*, *>) {
        dbAdmin.session {
            val repo = ConfigRepository(it)
            var config = repo.findOneByPid(pid)

            if (config == null) {
                config = ConfigEntity()
                config.pid = pid
            }

            config.properties = properties as Dictionary<String, Any>

            repo.save(config)
        }
    }

    override fun exists(pid: String): Boolean {
        return dbAdmin.session { ConfigRepository(it).findOneByPid(pid) != null }
    }

    override fun load(pid: String): Dictionary<*, *>? {
        return dbAdmin.session {
            return@session ConfigRepository(it).findOneByPid(pid)?.properties ?: null
        }
    }

    override fun delete(pid: String) {
        dbAdmin.session {
            val repo = ConfigRepository(it)
            repo.findOneByPid(pid)?.let { repo.delete(it) }
        }
    }

    override fun getDictionaries(): Enumeration<*> {
        return dbAdmin.session {
            return@session Collections.enumeration(ConfigRepository(it).findAll().map { it.properties })
        }
    }
}