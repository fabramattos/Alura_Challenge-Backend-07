package br.com.jornadamilhas.api.domain.depoimento

object DtoAtualizacaoDepoimentoTest {

    fun build(id: Long) = DtoAtualizacaoDepoimento(
        id = id,
        nome = "nome atualizado",
        fotoUrl = "http://www.foto1atualizada.com.br",
        depoimento = "depoimento atualizado"
    )
}