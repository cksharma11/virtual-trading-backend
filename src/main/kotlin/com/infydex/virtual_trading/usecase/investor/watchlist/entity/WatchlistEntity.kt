package com.infydex.virtual_trading.usecase.investor.watchlist.entity

import javax.persistence.*

@Entity
@Table(name = "watchlist")
data class WatchlistEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int? = null,

    @Column(name = "investor_id")
    val investorId: Int,

    @Column(name = "stock")
    val stock: String
) {
    constructor() : this(null, 1, "")
}
