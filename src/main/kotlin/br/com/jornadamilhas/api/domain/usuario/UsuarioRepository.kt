package br.com.jornadamilhas.api.domain.usuario

import org.springframework.data.jpa.repository.JpaRepository

interface UsuarioRepository : JpaRepository<Usuario, Long> {
    fun findByLogin(login: String?): Usuario?
}