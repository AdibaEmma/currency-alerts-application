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
    var userId: Long?,
    var uuid: String,
    var firstName: String,
    var lastName: String,
    var email: String,
    private var username: String,
    private var password: String,
    private var locked: Boolean = false,
    private var enabled: Boolean = true,
) :
    UserDetails {

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.MERGE])
    @JoinTable(name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")])
    var roles: Set<Role>? = null

    fun addRoleToUser(role: Role) {
        if (this.roles?.contains(role)!!) throw RoleExistsException("user already has $role.name role")
        this.roles = this.roles?.plus(role)
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        val authorities: MutableCollection<SimpleGrantedAuthority> = ArrayList()
        this.roles?.forEach { role -> authorities.add(SimpleGrantedAuthority(role.name)) }
        return authorities
    }

    override fun getPassword(): String {
        return this.password
    }

    fun setPassword(newPassword: String) {
        this.password = newPassword
    }

    override fun getUsername(): String {
        return this.username
    }

    fun setUsername(newUsername: String) {
        this.username = newUsername
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

    fun toggleEnabled(enabled: Boolean) {
        this.enabled = enabled
    }
}