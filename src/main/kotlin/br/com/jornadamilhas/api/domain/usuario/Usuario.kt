package br.com.jornadamilhas.api.domain.usuario

import br.com.jornadamilhas.api.domain.role.Role
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.springframework.security.core.userdetails.UserDetails

@Entity(name = "Usuario")
class Usuario(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val nome: String,
    val login: String,
    val senha: String,

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "usuario_role")
    val role: List<Role> = mutableListOf(),
) : UserDetails {
    override fun getAuthorities() = role

    override fun getPassword() = senha

    override fun getUsername() = login

    override fun isAccountNonExpired() = true


    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true

}
