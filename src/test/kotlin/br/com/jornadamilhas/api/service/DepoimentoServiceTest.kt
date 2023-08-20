package br.com.jornadamilhas.api.service

import br.com.jornadamilhas.api.domain.depoimento.DepoimentoRepository
import br.com.jornadamilhas.api.domain.depoimento.DepoimentoTest
import br.com.jornadamilhas.api.domain.depoimento.DtoAtualizacaoDepoimento
import br.com.jornadamilhas.api.infra.exception.DepoimentoNaoEncontradoException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import java.util.*

class DepoimentoServiceTest {

    private val repository = mockk<DepoimentoRepository> {}
    private val depoimento = DepoimentoTest.build()
    private val service = DepoimentoService(repository)

    @Test
    @DisplayName("Dado um BD com menos de 3 depoimentos, Quando solicitado 3 aleatorios, Deve retornar o total disponivel")
    fun escolheTresAleatoriosCenario1() {
        val depoimentos = DepoimentoTest.buildLista(2)
        every { repository.findAll() } returns depoimentos

        service.escolheTresAleatorios()

        verify(exactly = 1) { repository.findAll() }
    }

    @Test
    @DisplayName("Dado um BD com mais de 3 depoimentos, Quando solicitado 3 aleatorios, Deve retornar 3 aleatorios")
    fun escolheTresAleatoriosCenario2() {
        val depoimentos = DepoimentoTest.buildLista(10)
        every { repository.findAll() } returns depoimentos

        val listaAleatoria1 = service.escolheTresAleatorios()
        val listaAleatoria2 = service.escolheTresAleatorios()

        verify(exactly = 2) { repository.findAll() }
        assertEquals(3, listaAleatoria1.size)
        assertEquals(3, listaAleatoria2.size)

        assertFalse(listaAleatoria1.containsAll(listaAleatoria2), "Deveriam ter conteudos diferentes")
    }

    @Test
    @DisplayName("Dado um id, deve retornar o depoimento")
    fun buscaPoIdCenario1() {
        every { repository.findById(any()) } returns Optional.of(depoimento)

        val depoimento = service.buscaPorId(1)

        verify(exactly = 1) { repository.findById(any()) }
        assertNotNull(depoimento, "deveria ter retornado um depoimento")
    }

    @Test
    @DisplayName("Dado um id inexistente, deve retornar exception")
    fun buscaPoIdCenario2() {
        every { repository.findById(any()) } returns Optional.empty()

        assertThrows<DepoimentoNaoEncontradoException> { service.buscaPorId(Mockito.anyLong()) }
    }

    @Test
    @DisplayName("Dado um DTO válido para atualização de depoimento, deve retornar o depoimento atualizado")
    fun atualizaDepoimento(){
        every { repository.findById(any()) } returns Optional.of(depoimento)

        val depoimentoAtualizado = service.atualiza(DtoAtualizacaoDepoimento(1, null, null, "ATUALIZADO"))

        verify (exactly = 1) {repository.findById(any())}
        assertEquals("ATUALIZADO", depoimentoAtualizado.depoimento)
    }

}