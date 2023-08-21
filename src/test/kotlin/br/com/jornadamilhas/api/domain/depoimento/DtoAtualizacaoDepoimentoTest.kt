package br.com.jornadamilhas.api.domain.depoimento

object DtoAtualizacaoDepoimentoTest {

    fun build() = DtoAtualizacaoDepoimento(
        id = 1,
        nome = "nome atualizado",
        fotoUrl = "http://www.foto1atualizada.com.br",
        depoimento = "depoimento atualizado"
    )
}