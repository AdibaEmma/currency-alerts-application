package com.aweperi.bayzatbeengineeringassignment.model

import javax.persistence.*

@Entity
@Table(name = "app_role")
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    val roleId: Long?,
    @Column(name = "role_name")
    val roleName: String,

    @ManyToMany(mappedBy = "roles")
    var users: MutableSet<User>? = null
)