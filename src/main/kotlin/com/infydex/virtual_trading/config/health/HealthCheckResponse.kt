package com.infydex.virtual_trading.config.health

data class HealthCheckResponse(
    val name: String,
    val healthy: HealthStatus
)
