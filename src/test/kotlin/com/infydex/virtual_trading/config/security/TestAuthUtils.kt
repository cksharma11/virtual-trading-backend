package com.infydex.virtual_trading.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

object TestAuthUtils {
    private const val DEFAULT_USER = "test-user"
    private val objectMapper: ObjectMapper = jacksonObjectMapper()

    fun createJWTPayload(
        username: String = DEFAULT_USER,
        entitlements: Array<String> = arrayOf("infydex.investor")
    ): String {
        val jwtPayload: Map<String, Any> =
            mapOf(
                "sub" to username,
                "user" to mapOf(
                    "entitlements" to entitlements,
                    "name" to arrayOf(username),
                )
            )
        return objectMapper.writeValueAsString(jwtPayload)
    }
}
