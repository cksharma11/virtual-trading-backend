package com.infydex.virtual_trading.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm

object JwtUtils {
    fun createInvestorJWT(id: Int, phone: String): String {
        val entitlements = arrayOf("infydex.investor")
        val jwtPayload: Map<String, Any> =
            mapOf(
                "sub" to id,
                "user" to mapOf(
                    "entitlements" to entitlements,
                    "phone" to arrayOf(phone),
                )
            )

        val algorithm: Algorithm = Algorithm.HMAC256("secret")

        return JWT.create()
            .withIssuer("auth0")
            .withHeader(jwtPayload)
            .sign(algorithm)
    }
}
