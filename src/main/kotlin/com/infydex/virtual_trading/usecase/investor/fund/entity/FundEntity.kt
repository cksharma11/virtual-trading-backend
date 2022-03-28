package com.infydex.virtual_trading.usecase.investor.fund.entity

import javax.persistence.*

enum class TransactionType {
    CREDIT, DEBIT
}

@Entity
@Table(name = "fund")
data class FundEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long? = null,

    @Column(name = "investor_id", nullable = false)
    val investorId: Int,

    @Column(name = "amount", nullable = false)
    val amount: Double,

    @Column(name = "transaction_type", nullable = false)
    val transactionType: TransactionType,
) {
    constructor() : this(null, 1, 1.0, TransactionType.CREDIT)
}
