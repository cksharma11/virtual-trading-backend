package com.infydex.virtual_trading.usecase.investor.onboarding.entity

import org.hibernate.annotations.CreationTimestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "investor")
data class InvestorEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int? = null,

    @Column(name = "name", nullable = true)
    val name: String? = null,

    @Column(name = "email", nullable = true)
    val email: String? = null,

    @Column(name = "phone", unique = true, nullable = false)
    val phone: String,

    @Column(name = "created_at")
    @CreationTimestamp
    val created_at: Date,
) {
    constructor() : this(null, null, null, "", Date())
}
