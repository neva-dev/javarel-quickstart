package com.neva.javarel.app.adm.content

import org.bson.types.ObjectId
import org.mongodb.morphia.annotations.*

@Entity("employees")
@Indexes(Index(value = "salary", fields = arrayOf(Field("salary"))))
class Employee {

    @Id
    lateinit var id: ObjectId

    val name: String

    @Property("wage")
    val salary: Int

    constructor() {
        this.name = ""
        this.salary = 0
    }

    constructor(name: String, salary: Int) {
        this.name = name
        this.salary = salary
    }
}