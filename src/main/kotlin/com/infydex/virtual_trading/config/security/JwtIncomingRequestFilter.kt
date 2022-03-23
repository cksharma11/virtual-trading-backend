package com.infydex.virtual_trading.config.security

import com.auth0.jwt.impl.JWTParser
import com.auth0.jwt.interfaces.Payload
import mu.KLoggable
import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtIncomingRequestFilter(private val contextPath: String) : UsernamePasswordAuthenticationFilter() {
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpRequest = request as HttpServletRequest

        if (isProtectedEndpoint(httpRequest)) {
            val jwtPayload = httpRequest.getHeader(X_JWT_PAYLOAD)
            if (jwtPayload != null) {
                try {
                    val payload = jwtParser.parsePayload(jwtPayload)
                    AuthenticationFacade.setAuthentication(payload.toAuthenticationPrincipal())
                } catch (exception: Exception) {
                    logger.error("Unauthorized request - ${request.requestURI}. Failed to parse JWT", exception)
                    (response as HttpServletResponse).sendError(HttpStatus.BAD_REQUEST.value())
                    return
                }
            }
        }

        chain.doFilter(request, response)
    }

    private fun isProtectedEndpoint(httpRequest: HttpServletRequest) =
        httpRequest.requestURI.matches(Regex(pattern = "$contextPath/api/.*"))

    companion object : KLoggable by KLogging() {
        val jwtParser = JWTParser()
        const val X_JWT_PAYLOAD = "x-jwt-payload"
    }
}

fun Payload.toAuthenticationPrincipal(): AuthenticationPrincipal {
    return AuthenticationPrincipal(
        username = this.subject,
        entitlements = extractEntitlements(this)
    )
}

private fun extractEntitlements(payload: Payload) =
    (payload.claims["user"]!!.asMap()["entitlements"] as List<*>)
        .map { SimpleGrantedAuthority("ROLE_$it") }
