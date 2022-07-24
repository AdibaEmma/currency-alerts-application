package com.aweperi.bayzatbeengineeringassignment.model

import org.hibernate.annotations.CreationTimestamp
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*
import javax.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "currency_alert")
class Alert(
    @OneToOne
    var currency: Currency?,
    @Column(name = "target_price")
    var targetPrice: BigDecimal,

    @Enumerated(value = EnumType.STRING)
    var status: AlertStatus,

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    var createdAt: LocalDateTime,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User? = null,

    @Id @GeneratedValue(strategy = IDENTITY)
    var alertId: Long?
)