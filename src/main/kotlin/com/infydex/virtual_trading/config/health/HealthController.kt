package com.infydex.virtual_trading.config.health

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthController {
    @GetMapping("/health/status")
    fun health(): HealthCheckResponse {
        return HealthCheckResponse(
            name = "Virtual Trading",
            healthy = HealthStatus.HEALTHY
        )
    }
}
