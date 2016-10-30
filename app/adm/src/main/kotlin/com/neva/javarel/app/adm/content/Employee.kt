package com.neva.javarel.app.adm.content

import org.mongodb.morphia.annotations.*

@Entity("employees")
@Indexes(Index(value = "salary", fields = arrayOf(Field("salary"))))
class Employee {

    @Id
    private lateinit var id: String

    private var name: String? = null

    @Reference
    private var manager: Employee? = null

    @Reference
    var directReports: MutableList<Employee>? = mutableListOf()

    @Property("wage")
    private var salary: Double? = null

    constructor() {
        // intentionally empty
    }

    constructor(name: String, salary: Double){
        this.name = name
        this.salary = salary
    }
}