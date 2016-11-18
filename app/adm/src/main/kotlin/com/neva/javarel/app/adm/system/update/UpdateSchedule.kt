package com.neva.javarel.app.adm.system.update

import com.neva.javarel.processing.scheduler.api.BaseSchedule
import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Service
import org.quartz.ScheduleBuilder
import kotlin.reflect.KClass

@Component(immediate = true)
@Service
class UpdateSchedule : BaseSchedule<UpdateJob>() {

    override val jobType: KClass<UpdateJob>
        get() = UpdateJob::class

    override fun schedule(): ScheduleBuilder<*> {
        return repeat { it.withIntervalInHours(6) }
    }

}