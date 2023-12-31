package br.com.jornadamilhas.api.domain.destino

import br.com.jornadamilhas.domain.destino.DtoAtualizacaoDestino
import java.math.BigDecimal

object DtoAtualizacaoDestinoTest {

    fun build(id: Long) = DtoAtualizacaoDestino(
        id = id,
        foto1Url = "http://www.foto1atualizada.com.br",
        foto2Url = "http://www.foto2atualizada.com.br",
        nome = "nome atualizado",
        meta = "descrição atualizada",
        descricao = "descricao atualizada",
        preco = BigDecimal("99.99")
    )
}