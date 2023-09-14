package br.com.jornadamilhas.api.integration

import br.com.jornadamilhas.api.domain.destino.DestinoTest
import br.com.jornadamilhas.domain.destino.DestinoRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class DestinoRepositoryTest(@Autowired private val repository: DestinoRepository)
    : DatabaseContainerConfiguration(){

    private val destino = DestinoTest.validoBuild()

    @BeforeEach
    fun setup(){
        repository.save(destino)
    }

    @Test
    @DisplayName("Dado nome existente, deve listar os destinos")
    fun busca1(){
        val destinos = repository.findAllByNome(destino.nome)

        Assertions.assertNotNull(destinos)
        Assertions.assertFalse(destinos.isEmpty)
    }

    @Test
    @DisplayName("Dado nome inexistente, deve retornar Optional vazio")
    fun busca2(){
        val destinos = repository.findAllByNome("nomeInexistente")

        Assertions.assertTrue(destinos.get().isEmpty())

    }
}