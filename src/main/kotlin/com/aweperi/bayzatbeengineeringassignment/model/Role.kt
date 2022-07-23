package com.aweperi.bayzatbeengineeringassignment.model

import org.hibernate.Hibernate
import javax.persistence.*

@Entity
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    val roleId: Long?,
    val name: String?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Role

        return roleId != null && roleId == other.roleId
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(name = $name )"
    }
}
