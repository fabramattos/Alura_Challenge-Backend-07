package br.com.jornadamilhas.api.integration

import br.com.jornadamilhas.api.domain.usuario.UsuarioTest
import br.com.jornadamilhas.domain.usuario.UsuarioRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class UsuarioRepositoryTest(@Autowired private val repository: UsuarioRepository)
    : DatabaseContainerConfiguration(){

    private val usuario = UsuarioTest.build()

    @BeforeEach
    fun setup(){
        repository.save(usuario)
    }

    @Test
    @DisplayName("Dado um email válido, Deve retornar usuario")
    fun login1() {
        val buscado = repository.findByLogin(usuario.login)

        Assertions.assertNotNull(buscado)
        Assertions.assertEquals(usuario.login, buscado?.login)
    }

    @Test
    @DisplayName("Dado um email inválido, Deve retornar usuario")
    fun login2() {
        val buscado = repository.findByLogin("loginInvalido")

        Assertions.assertNull(buscado)
    }
}