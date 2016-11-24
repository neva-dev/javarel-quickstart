package com.neva.javarel.app.adm.structure

import com.neva.javarel.framework.api.structure.Module
import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Service

@Service
@Component(immediate = true)
class AdmModule : Module {

    companion object {
        const val PRIORITY = 100
    }

    override val priority: Int
        get() = PRIORITY

    override val name: String
        get() = "adm"

    override val options: Map<String, Any>
        get() = mapOf(
                "route" to "adm.home",
                "label" to "Administration",
                "type" to "application"
        )

}