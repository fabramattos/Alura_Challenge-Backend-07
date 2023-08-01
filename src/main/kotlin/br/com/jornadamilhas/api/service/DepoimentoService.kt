package br.com.jornadamilhas.api.service

import br.com.jornadamilhas.api.domain.depoimento.Depoimento
import br.com.jornadamilhas.api.domain.depoimento.DepoimentoRepository
import br.com.jornadamilhas.api.domain.depoimento.DtoAtualizacaoDepoimento
import br.com.jornadamilhas.api.domain.depoimento.DtoCadastroDepoimento
import br.com.jornadamilhas.api.infra.exception.DepoimentoNaoEncontradoException
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class DepoimentoService(private val repository: DepoimentoRepository) {

    fun escolheTresAleatorios() : List<Depoimento> {
        val depoimentos = repository.findAll()
        return getDepoimentosAleatoriosUnicos(3, depoimentos)
    }

    private fun getDepoimentosAleatoriosUnicos(qtdeDesejada: Int, depoimentos: List<Depoimento>) : List<Depoimento> {
        if(qtdeDesejada >= depoimentos.size)
            return depoimentos

        val random = Random(System.currentTimeMillis())
        val setAleatorio = mutableSetOf<Depoimento>()

        while (setAleatorio.size < qtdeDesejada){
            setAleatorio.add(depoimentos[random.nextInt(depoimentos.size)])
        }

        return setAleatorio.toList()

    }

    fun salvar(dados: DtoCadastroDepoimento) = repository.save(Depoimento(dados))
    fun buscaPorId(id: Long) = repository
        .findById(id)
        .orElseThrow { DepoimentoNaoEncontradoException() }

    fun atualiza(dados: DtoAtualizacaoDepoimento) : Depoimento =
        buscaPorId(dados.id)
            .atualizarInformacoes(dados)

    fun deletar(id: Long) = repository
        .delete(buscaPorId(id))
}