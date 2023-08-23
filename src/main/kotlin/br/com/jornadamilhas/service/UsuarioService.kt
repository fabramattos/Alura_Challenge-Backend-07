package br.com.jornadamilhas.service

import br.com.jornadamilhas.domain.usuario.UsuarioRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UsuarioService(private val repository: UsuarioRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails =
        repository.findByLogin(username) ?: throw UsernameNotFoundException("Usuário não encontrado")
}