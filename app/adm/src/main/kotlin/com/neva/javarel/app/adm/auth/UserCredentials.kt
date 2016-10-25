package com.neva.javarel.app.adm.auth

import com.neva.javarel.security.auth.api.Credentials
import javax.ws.rs.FormParam

data class UserCredentials(
        @FormParam("user.principal") override val principal: String,
        @FormParam("user.remember") override val remember: Boolean,
        @FormParam("user.password") val password: String
) : Credentials