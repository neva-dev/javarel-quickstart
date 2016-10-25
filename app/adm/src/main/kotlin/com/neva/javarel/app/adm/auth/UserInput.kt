package com.neva.javarel.app.adm.auth

import javax.ws.rs.FormParam

data class UserInput(
        @FormParam("user.email") val email: String,
        @FormParam("user.password") val password: String,
        @FormParam("user.name") val name: String,
        @FormParam("user.nick") val nick: String?
)
