package br.com.jornadamilhas.service

import br.com.jornadamilhas.domain.destino.Destino
import br.com.jornadamilhas.domain.destino.DestinoRepository
import br.com.jornadamilhas.domain.destino.DtoAtualizacaoDestino
import br.com.jornadamilhas.domain.destino.DtoCadastroDestino
import br.com.jornadamilhas.infra.exception.DestinoNaoEncontradoException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class DestinoService(private val repository: DestinoRepository, private val gptService : GptService) {
    fun save(dados: DtoCadastroDestino): Destino {
        if (dados.descricao.isNullOrEmpty())
            dados.descricao = gptService.geraDescricao(dados.nome)
        val destino = Destino(dados)
        return repository.save(destino)
    }

    fun buscaPorNome(nome: String): List<Destino> =
        repository
            .findAllByNome(nome)
            .orElseThrow { DestinoNaoEncontradoException() }

    fun buscaPorId(id: Long): Destino = repository
        .findById(id)
        .orElseThrow { DestinoNaoEncontradoException() }

    fun atualiza(dados: DtoAtualizacaoDestino): Destino =
        buscaPorId(dados.id)
            .atualiza(dados)

    fun delete(id: Long) {
        val destino = buscaPorId(id)
        repository.delete(destino)
    }

    fun busca(paginacao: Pageable): Page<Destino> {
        val destinos = repository.findAll(paginacao)

        if (destinos.isEmpty)
            throw DestinoNaoEncontradoException()

        return destinos
    }

}