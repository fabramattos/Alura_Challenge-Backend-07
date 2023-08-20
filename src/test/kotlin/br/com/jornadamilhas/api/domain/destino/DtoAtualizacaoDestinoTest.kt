package br.com.jornadamilhas.api.domain.destino

import java.math.BigDecimal

object DtoAtualizacaoDestinoTest {

    fun atualizaTudoBuild() = DtoAtualizacaoDestino(
        id = 1,
        foto1Url = "http://www.foto1atualizada.com.br",
        foto2Url = "http://www.foto2atualizada.com.br",
        nome = "nome atualizado",
        meta = "descrição atualizada",
        descricao = "descricao atualizada",
        preco = BigDecimal("200.00")
    )
}