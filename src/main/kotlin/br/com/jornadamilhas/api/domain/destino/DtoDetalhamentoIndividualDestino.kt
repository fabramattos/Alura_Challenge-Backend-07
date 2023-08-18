package br.com.jornadamilhas.api.domain.destino

data class DtoDetalhamentoIndividualDestino(val id: Long?,
                                            val foto1Url: String,
                                            val foto2Url: String,
                                            var nome: String,
                                            var meta: String,
                                            var descricao: String? = null)
{
    constructor(destino : Destino)
        : this(
            destino.id,
            destino.foto1Url,
            destino.foto2Url,
            destino.nome,
            destino.meta,
            destino.descricao,
        )
}


