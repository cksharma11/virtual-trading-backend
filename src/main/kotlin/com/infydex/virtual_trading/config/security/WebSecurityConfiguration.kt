package com.infydex.virtual_trading.config.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy

@Configuration
@EnableWebSecurity
class WebSecurityConfiguration : WebSecurityConfigurerAdapter() {
    @Value("\${server.servlet.context-path}")
    lateinit var contextPath: String

    val publicEndpoints = arrayOf(
        "/health/**",
        "/api/v1/investor/signup"
    )

    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers(*publicEndpoints)
            .permitAll()
            .antMatchers("/**").access("hasRole('infydex.investor')")
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilter(JwtIncomingRequestFilter(contextPath))
    }
}
