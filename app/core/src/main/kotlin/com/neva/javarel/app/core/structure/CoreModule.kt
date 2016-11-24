package com.neva.javarel.app.core.structure

import com.neva.javarel.framework.api.structure.Module
import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Service

@Service
@Component(immediate = true)
class CoreModule : Module {

    companion object {
        const val PRIORITY = 0
    }

    override val priority: Int
        get() = PRIORITY

    override val name: String
        get() = "adm"

    override val options: Map<String, Any>
        get() = mapOf(
                "route" to "adm.home",
                "label" to "Core",
                "type" to "extension"
        )

}