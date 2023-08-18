package br.com.jornadamilhas.api.domain.depoimento

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

    fun build() = Depoimento(
        id = 1,
        nome = "Melon Husk",
        foto = "http://www.fotoUrl.com.br",
        depoimento = "Depoimento"
    )
}