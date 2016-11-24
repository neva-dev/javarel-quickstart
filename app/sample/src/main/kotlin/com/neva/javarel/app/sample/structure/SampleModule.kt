package com.neva.javarel.app.sample.structure

import com.neva.javarel.framework.api.structure.Module
import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Service

@Service
@Component(immediate = true)
class SampleModule : Module {

    companion object {
        const val PRIORITY = 200
    }

    override val priority: Int
        get() = PRIORITY

    override val name: String
        get() = "sample"

    override val options: Map<String, Any>
        get() = mapOf(
                "route" to "sample.home",
                "label" to "Samples",
                "type" to "application"
        )

}