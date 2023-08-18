package br.com.jornadamilhas.api.domain.destino

import java.math.BigDecimal

data class DtoDetalhamentoDestino(val id: Long?,
                                  val foto1Url: String,
                                  val foto2Url: String,
                                  var nome: String,
                                  var meta: String,
                                  var descricao: String? = null,
                                  var preco: BigDecimal)
{
    constructor(destino : Destino)
        : this(
            destino.id,
            destino.foto1Url,
            destino.foto2Url,
            destino.nome,
            destino.meta,
            destino.descricao,
            destino.preco
        )
}


