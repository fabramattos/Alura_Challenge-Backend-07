package br.com.jornadamilhas.api.domain.destino

import java.math.BigDecimal

data class DtoDetalhamentoDestino(val id: Long, val fotoUrl: String, val nome: String, val preco: BigDecimal){
    constructor(destino : Destino) : this(destino.id, destino.foto, destino.nome, destino.preco)
}


