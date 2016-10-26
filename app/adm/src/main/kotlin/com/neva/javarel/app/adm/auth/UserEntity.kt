package com.neva.javarel.app.adm.auth

import com.neva.javarel.security.auth.api.Authenticable
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "adm_auth_user")
open class UserEntity : Authenticable {

    companion object {
        const val ID_COLUMN = "id"
        const val EMAIL_COLUMN = "email"
        const val PRINCIPAL_COLUMN = "principal"
        const val PASSWORD_COLUMN = "password"
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_COLUMN)
    lateinit var id: Integer

    @Column(name = EMAIL_COLUMN, nullable = false, unique = true)
    lateinit var email: String

    @Column(length = 64, nullable = false)
    lateinit var password: String

    @Column(length = 80, nullable = false)
    lateinit var salt: String

    @Column(name = PRINCIPAL_COLUMN, nullable = false, unique = true)
    override lateinit var principal: String

    @Column(nullable = true)
    var nick: String? = null

    @Column
    lateinit var birth: Date

    constructor() {
        // default constructor
    }

    val displayName: String
        get() = if (!nick.isNullOrBlank()) nick!! else principal

    override fun toString(): String {
        return "UserEntity(id=$id, email='$email', principal='$principal', nick=$nick, birth=$birth)"
    }

}