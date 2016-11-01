package com.neva.javarel.app.adm.content

import org.bson.types.ObjectId
import org.mongodb.morphia.annotations.*

@Entity("employees")
@Indexes(Index(value = "salary", fields = arrayOf(Field("salary"))))
class Employee {

    @Id
    lateinit var id: ObjectId

    var name: String

    @Property("wage")
    var salary: Int

    @Reference
    var manager: Employee? = null

    @Reference
    val directReports: MutableList<Employee> = mutableListOf()

    constructor() {
        this.name = ""
        this.salary = 0
    }

    constructor(name: String, salary: Int) {
        this.name = name
        this.salary = salary
    }
}