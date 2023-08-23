package br.com.jornadamilhas.api.domain.depoimento

import br.com.jornadamilhas.domain.depoimento.DtoCadastroDepoimento

object DtoCadastroDepoimentoTest {

    fun build() = DtoCadastroDepoimento(
        nome = "Melon Husk",
        fotoUrl = "http://www.fotoUrl.com.br",
        depoimento = "Depoimento"
    )
}