package com.neva.javarel.app.adm.system.update;

import com.neva.javarel.foundation.api.injection.Osgi
import com.neva.javarel.storage.database.api.DatabaseAdmin
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.slf4j.LoggerFactory

class UpdateJob : Job {

    @Osgi
    private lateinit var db : DatabaseAdmin

    companion object {
        val LOG = LoggerFactory.getLogger(UpdateJob::class.java)
    }

    override fun execute(context: JobExecutionContext) {
        LOG.info("Checking for system update...")

        // TODO Implement system update functionality

        LOG.info("Nothing to update")
    }

}