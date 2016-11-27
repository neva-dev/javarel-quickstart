package com.neva.javarel.app.adm.system.config

import com.google.gson.reflect.TypeToken
import com.neva.javarel.foundation.api.media.json.Json
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "adm_system_config")
class ConfigEntity {

    companion object {
        const val PID_COLUMN = "pid"
        val PROPERTIES_TYPE = object : TypeToken<Hashtable<*, *>>() {}.type
    }

    @Id
    var id: Long = 0

    @Column(name = PID_COLUMN)
    lateinit var pid: String

    @Column(name = "properties")
    private var _properties: String = Json.EMPTY

    var properties: Dictionary<*, *>
        get() = Json.unserialize(_properties, PROPERTIES_TYPE)
        set(value) {
            Json.serialize(_properties)
        }

}