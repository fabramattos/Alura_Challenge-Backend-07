package br.com.jornadamilhas.api.service

import br.com.jornadamilhas.api.domain.destino.DestinoRepository
import br.com.jornadamilhas.api.domain.destino.DestinoTest
import br.com.jornadamilhas.api.domain.destino.DtoAtualizacaoDestinoTest
import br.com.jornadamilhas.api.domain.destino.DtoCadastroDestinoTest
import br.com.jornadamilhas.api.infra.exception.DestinoNaoEncontradoException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import java.util.*

class DestinoServiceTest {
    private val repository = mockk<DestinoRepository>()
    private val geradorDescricao = mockk<GeradorDescricao>() {
        every { geraDescricao() } returns "descricao mockkada"
    }
    private val destino = DestinoTest.validoBuild()
    private val service = DestinoService(repository, geradorDescricao)

    @Test
    @DisplayName("Dado um DtoDestino valido, deve salvar")
    fun save1() {
        every { repository.save(any()) } returns destino

        service.save(DtoCadastroDestinoTest.validoBuild())

        verify(exactly = 1) { repository.save(any()) }
        verify(exactly = 0) { geradorDescricao.geraDescricao() }
    }

    @Test
    @DisplayName("Dado um DtoDestino sem descricao, deve utilizar gerador de descricao")
    fun save2() {
        every { repository.save(any()) } returns destino
        service.save(DtoCadastroDestinoTest.semDescricaoBuild())

        verify(exactly = 1) { repository.save(any()) }
        verify(exactly = 1) { geradorDescricao.geraDescricao() }
    }

    @Test
    @DisplayName("Dado um nome válido, retorna lista de destinos")
    fun buscaPorNome1() {
        every { repository.findAllByNome(any()) } returns Optional.of(listOf(destino))

        service.buscaPorNome("Brasil")

        verify(exactly = 1) { repository.findAllByNome(any()) }
    }

    @Test
    @DisplayName("Dado um nome inválido, lança exception")
    fun buscaPorNome2() {
        every { repository.findAllByNome(any()) } returns Optional.empty()

        assertThrows<DestinoNaoEncontradoException> { service.buscaPorNome(Mockito.anyString()) }
    }

    @Test
    @DisplayName("Dado um id válido, retorna o destino")
    fun buscaPorId1() {
        every { repository.findById(any()) } returns Optional.of(destino)

        service.buscaPorId(1L)

        verify(exactly = 1) { repository.findById(any()) }

    }

    @Test
    @DisplayName("Dado um id inválido, lança exception")
    fun buscaPorId2() {
        every { repository.findById(any()) } returns Optional.empty()

        assertThrows<DestinoNaoEncontradoException> { service.buscaPorId(Mockito.anyLong()) }
    }

    @Test
    @DisplayName("Dado um Dto para atualização, deve atualizar todos campos necessários")
    fun atualiza() {
        val form = DtoAtualizacaoDestinoTest.atualizaTudoBuild()
        every { repository.findById(any()) } returns Optional.of(destino)

        val destinoAtualizado = service.atualiza(form)

        verify(exactly = 1) { repository.findById(any()) }
        Assertions.assertEquals(form.nome, destinoAtualizado.nome)
        Assertions.assertEquals(form.foto1Url, destinoAtualizado.foto1Url)
        Assertions.assertEquals(form.foto2Url, destinoAtualizado.foto2Url)
        Assertions.assertEquals(form.meta, destinoAtualizado.meta)
        Assertions.assertEquals(form.descricao, destinoAtualizado.descricao)
        Assertions.assertEquals(form.preco, destinoAtualizado.preco)
    }

}