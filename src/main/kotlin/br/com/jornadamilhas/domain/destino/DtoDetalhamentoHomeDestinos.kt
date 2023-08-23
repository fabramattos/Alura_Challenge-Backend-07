package br.com.jornadamilhas.domain.destino

import java.math.BigDecimal

data class DtoDetalhamentoHomeDestinos(val id: Long?,
                                       val foto1Url: String,
                                       var nome: String,
                                       var preco: BigDecimal)
{
    constructor(destino : Destino)
        : this(
            destino.id,
            destino.foto1Url,
            destino.nome,
            destino.preco
        )
}


