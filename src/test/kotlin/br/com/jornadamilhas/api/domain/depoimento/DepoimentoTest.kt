package br.com.jornadamilhas.api.domain.depoimento

import br.com.jornadamilhas.domain.depoimento.Depoimento

object DepoimentoTest {

    fun buildLista(qtde: Int): List<Depoimento> {
        var lista = emptyList<Depoimento>()
        for (i in 1..qtde) {
            lista = lista.plus(
                Depoimento(
                    id = i.toLong(),
                    nome = "nome $i",
                    foto = "http://www.foto${i}Url.com.br",
                    depoimento = "Depoimento $i"
                )
            )
        }
        return lista
    }

    fun build() : Depoimento {
        val depoimento = Depoimento(DtoCadastroDepoimentoTest.build())
        depoimento.id = 1L
        return depoimento
    }
}