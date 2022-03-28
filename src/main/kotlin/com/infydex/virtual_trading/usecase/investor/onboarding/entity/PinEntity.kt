package com.infydex.virtual_trading.usecase.investor.onboarding.entity

import javax.persistence.*

@Entity
@Table(name = "pin")
data class PinEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long? = null,

    @Column(name = "pin", nullable = false)
    val pin: String,

    @Column(name = "investor_id", nullable = false)
    val investorId: Int,

) {
    constructor() : this(null, "", 1)
}
