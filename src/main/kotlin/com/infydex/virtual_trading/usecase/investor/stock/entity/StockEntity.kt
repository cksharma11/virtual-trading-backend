package com.infydex.virtual_trading.usecase.investor.stock.entity

import com.infydex.virtual_trading.usecase.investor.stock.dto.StockTransactionType
import javax.persistence.*

@Entity
@Table(name = "investment")
data class StockEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long? = null,

    @Column(name = "investor_id", nullable = false)
    val investorId: Int,

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    val type: StockTransactionType,

    @Column(name = "stock_symbol", nullable = false)
    val stockSymbol: String,

    @Column(name = "price", nullable = false)
    val price: Double,

    @Column(name = "quantity", nullable = false)
    val quantity: Int,

    @Column(name = "status", nullable = false)
    val status: TransactionStatus,
) {
    constructor() : this(null, 1, StockTransactionType.BUY, "", 0.0, 0, TransactionStatus.COMPLETED)
}

enum class TransactionStatus {
    PENDING, COMPLETED, FAILED
}
