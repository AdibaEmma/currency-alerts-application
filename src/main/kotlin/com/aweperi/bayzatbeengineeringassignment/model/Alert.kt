package com.aweperi.bayzatbeengineeringassignment.model

import org.hibernate.annotations.CreationTimestamp
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*
import javax.persistence.GenerationType.IDENTITY
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Table(name = "currency_alert")
class Alert(
    @OneToOne
    var currency: Currency?,
    @NotNull(message = "target price cannot be null")
    @Column(name = "target_price")
    var targetPrice: BigDecimal,

    @Enumerated(value = EnumType.STRING)
    @NotBlank(message = "status cannot be null")
    var status: AlertStatus,

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    var createdAt: LocalDateTime,

    @ManyToOne(targetEntity = User::class)
    @JoinColumn(name = "user_id")
    var user: User? = null,

    @Id @GeneratedValue(strategy = IDENTITY)
    var alertId: Long?
)