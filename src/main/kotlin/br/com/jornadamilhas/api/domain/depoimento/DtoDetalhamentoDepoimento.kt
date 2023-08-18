package br.com.jornadamilhas.api.domain.depoimento
data class DtoDetalhamentoDepoimento(val id: Long?, val fotoUrl: String, val nome:String, val depoimento: String){
    constructor(depoimento: Depoimento) : this (depoimento.id, depoimento.foto, depoimento.nome, depoimento.depoimento)
}
