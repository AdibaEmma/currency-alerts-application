package com.aweperi.bayzatbeengineeringassignment.model

import com.aweperi.bayzatbeengineeringassignment.error_handling.exceptions.RoleExistsException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private val userId: Long?,
    private val locked: Boolean = false,
    private val enabled: Boolean = false,
    private val firstName: String?,
    private val lastName: String?,
    private val email: String?,
    private val password: String,
) :
    UserDetails {


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")])
    private var roles: List<Role> = ArrayList()
    fun addRoleToUser(role: Role) {
        if (this.roles.contains(role)) throw RoleExistsException("user already has $role.name role")
        this.roles += role
    }

    override fun getAuthorities(): Collection<GrantedAuthority?> {
        val authorities: MutableCollection<SimpleGrantedAuthority?> = ArrayList()
        this.roles.forEach { role -> authorities.add(SimpleGrantedAuthority(role.name)) }
        return authorities
    }

    override fun getPassword(): String {
        return this.password
    }

    override fun getUsername(): String {
        return this.email!!
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return !this.locked
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return this.enabled
    }
}