# Scheduling

For asynchronous processing, Javarel Framework is using Quartz scheduler.

To do something asynchronously, we just need to define two classes:

**Schedule**

Defines job type and how it should be scheduled.

```

@Component(immediate = true)
@Service
class UpdateSchedule : BaseSchedule<UpdateJob>() {

    override val jobType: KClass<UpdateJob>
        get() = UpdateJob::class

    override fun schedule(): ScheduleBuilder<*> {
        return repeat { it.withIntervalInHours(6) }
    }

}

```

What is more, you can use above methods to schedule job in other ways:

```
repeat { it.withIntervalInSeconds(30).withRepeatCount(10) }
cron("0 0 0/1 1/1 * ? *")
daily { it.onMondayThroughFriday() }
calendar { it.withIntervalInDays(1).inTimeZone(TimeZone.getTimeZone("EST")) } }
```

**Job**

Job is being created for each schedule occurrence. 
Mark fields with `@Osgi` annotation to inject services that will help you with your job processing.

```
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
```