package com.aweperi.bayzatbeengineeringassignment.model

import lombok.Data
import javax.persistence.*

@Entity
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    val roleId: Long?,
    val name: String?
)
