package com.aweperi.bayzatbeengineeringassignment.model

import org.hibernate.annotations.CreationTimestamp
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*
import javax.persistence.GenerationType.IDENTITY

@Entity
class Currency (
    var name: String,
    var symbol: String,
    var currentPrice: BigDecimal = BigDecimal.ZERO,
    var enabled: Boolean = true,

    @CreationTimestamp
    @Column(updatable = false)
    var createdTime: LocalDateTime = LocalDateTime.now(),
    @Id @GeneratedValue(strategy = IDENTITY) var id: Long?
)