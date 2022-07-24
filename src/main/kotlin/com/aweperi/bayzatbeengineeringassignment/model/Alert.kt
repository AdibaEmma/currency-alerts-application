package com.aweperi.bayzatbeengineeringassignment.model

import org.hibernate.annotations.CreationTimestamp
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Alert(
    @OneToOne
    var currency: Currency?,
    var targetPrice: BigDecimal,

    @Enumerated(value = EnumType.STRING)
    var status: AlertStatus,

    @CreationTimestamp
    @Column(updatable = false)
    var createdAt: LocalDateTime,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var alertId: Long?,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User? = null
)