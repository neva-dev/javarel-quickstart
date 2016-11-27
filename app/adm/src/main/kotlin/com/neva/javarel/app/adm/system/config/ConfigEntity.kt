package com.neva.javarel.app.adm.system.config

import com.google.gson.reflect.TypeToken
import com.neva.javarel.foundation.api.media.json.Json
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "adm_system_config")
class ConfigEntity {

    companion object {
        const val PID_COLUMN = "pid"
        val PROPERTIES_TYPE = object : TypeToken<Hashtable<String, Any>>() {}.type
    }

    @Id
    @GeneratedValue
    var id: Long = 0

    @Column(name = PID_COLUMN)
    lateinit var pid: String

    @Column(name = "properties")
    private var _properties: String = Json.EMPTY

    var properties: Dictionary<String, Any>
        get() = Json.unserialize(_properties, PROPERTIES_TYPE)
        set(value) {
            Json.serialize(_properties)
        }

    fun updateProperties(mapper: (MutableMap<String, Any>) -> Unit) {
        val map = mutableMapOf<String, Any>()
        val actual = properties

        for (key in actual.keys()) {
            map.put(key, actual.get(key))
        }

        mapper(map)

        val updated = Hashtable<String, Any>()
        for ((key, value) in map) {
            updated[key] = value
        }

        properties = updated
    }

}