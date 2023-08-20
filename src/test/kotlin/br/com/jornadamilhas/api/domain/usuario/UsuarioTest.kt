package br.com.jornadamilhas.api.domain.usuario

import br.com.jornadamilhas.api.domain.role.Role

object UsuarioTest {

    fun build() = Usuario(
        id = 1,
        nome = "admin",
        login = "admin@email.com",
        senha = "123456",
        role = mutableListOf(Role(1, "ADMIN"))
    )
}