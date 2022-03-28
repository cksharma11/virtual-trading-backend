package com.infydex.virtual_trading.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

object TestAuthUtils {
    private const val DEFAULT_USER = "test-user"
    private val objectMapper: ObjectMapper = jacksonObjectMapper()

    fun createJWTPayload(
        userId: String = DEFAULT_USER,
        entitlements: Array<String> = arrayOf("infydex.investor")
    ): String {
        val jwtPayload: Map<String, Any> =
            mapOf(
                "sub" to userId,
                "user" to mapOf(
                    "entitlements" to entitlements,
                    "name" to arrayOf(userId),
                )
            )
        return objectMapper.writeValueAsString(jwtPayload)
    }
}
