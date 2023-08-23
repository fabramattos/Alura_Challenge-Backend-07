package br.com.jornadamilhas.api.domain.depoimento

import br.com.jornadamilhas.domain.depoimento.DtoAtualizacaoDepoimento

object DtoAtualizacaoDepoimentoTest {

    fun build(id: Long) = DtoAtualizacaoDepoimento(
        id = id,
        nome = "nome atualizado",
        fotoUrl = "http://www.foto1atualizada.com.br",
        depoimento = "depoimento atualizado"
    )
}