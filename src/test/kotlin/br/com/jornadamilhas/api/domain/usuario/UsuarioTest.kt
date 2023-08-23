package br.com.jornadamilhas.api.domain.usuario

import br.com.jornadamilhas.domain.role.Role
import br.com.jornadamilhas.domain.usuario.Usuario

object UsuarioTest {

    fun build() = Usuario(
        id = 1,
        nome = "admin",
        login = "admin@email.com",
        senha = "123456",
        role = mutableListOf(Role(1, "ADMIN"))
    )
}