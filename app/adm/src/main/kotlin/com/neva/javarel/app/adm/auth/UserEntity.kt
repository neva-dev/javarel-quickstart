package com.neva.javarel.app.adm.auth

import com.neva.javarel.security.auth.api.Authenticable
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "adm_auth_user")
open class UserEntity : Authenticable {

    companion object {
        const val EMAIL_COLUMN = "email"
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    lateinit var id: Integer

    @Column(name = EMAIL_COLUMN, nullable = false)
    lateinit var email: String

    @Column(length = 64, nullable = false)
    lateinit var password: String

    @Column(length = 80, nullable = false)
    lateinit var salt: String

    @Column
    lateinit var name: String

    @Column
    lateinit var birth: Date

    constructor() {
        // default constructor
    }

    override val authIdentifier: String
        get() = email
    override val authPassword: String
        get() = password

}