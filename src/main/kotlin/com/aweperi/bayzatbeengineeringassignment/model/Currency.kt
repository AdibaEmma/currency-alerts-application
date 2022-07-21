package com.aweperi.bayzatbeengineeringassignment.model

import com.benasher44.uuid.Uuid
import org.hibernate.annotations.CreationTimestamp
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id

@Entity
class Currency(
    var name: String,
    var symbol: String,
    var currentPrice: BigDecimal = BigDecimal.ZERO,
    var enabled: Boolean = true,

    @CreationTimestamp
    @Column(updatable = false)
    var createdTime: LocalDateTime = LocalDateTime.now(),
    var uuid: String?,
    @Id @GeneratedValue(strategy = IDENTITY) var currencyId: Long?
)