package com.infydex.virtual_trading.config.security

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class PrincipalProvider {
    fun isAuthenticated(): Boolean = (AuthenticationFacade.getAuthentication() != null)
    fun userName(): String = AuthenticationFacade.getAuthentication()!!.username
}

data class AuthenticationPrincipal(
    val username: String,
    val entitlements: List<GrantedAuthority>,
) :
    UsernamePasswordAuthenticationToken(username, null, entitlements)

internal object AuthenticationFacade {
    fun setAuthentication(authenticationPrincipal: AuthenticationPrincipal?) {
        SecurityContextHolder.getContext().authentication = authenticationPrincipal
    }

    fun getAuthentication(): AuthenticationPrincipal? {
        val auth = SecurityContextHolder.getContext().authentication
        if (auth is AuthenticationPrincipal) return auth
        return null
    }
}
