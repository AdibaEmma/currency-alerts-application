package com.aweperi.bayzatbeengineeringassignment.model

import org.hibernate.annotations.CreationTimestamp
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
class Currency(
    @NotBlank(message = "currency name cannot be empty or null")
    var name: String,
    @NotBlank(message = "currency symbol cannot be empty or null")
    var symbol: String,
    @Column(name = "current_price")
    @NotNull(message = "currency price cannot be or null")
    var currentPrice: BigDecimal = BigDecimal.ZERO,
    var enabled: Boolean = true,

    @CreationTimestamp
    @Column(name = "created_time", updatable = false)
    var createdTime: LocalDateTime = LocalDateTime.now(),
    var uuid: String?,
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "currency_id")
    var currencyId: Long?
)