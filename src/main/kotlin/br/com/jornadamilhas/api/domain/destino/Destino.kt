package br.com.jornadamilhas.api.domain.destino

import jakarta.persistence.*
import java.math.BigDecimal

@Table(name = "destinos")
@Entity(name = "Destino")
class Destino(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long,
    var foto1Url: String,
    var foto2Url: String,
    var nome: String,
    var meta: String,
    var descricao: String? = null,
    var preco: BigDecimal)

{

    constructor(dados : DtoCadastroDestino)
            : this(0L, dados.foto1Url, dados.foto2Url, dados.nome, dados.meta, dados.descricao, dados.preco)

    fun atualiza(dados : DtoAtualizacaoDestino) : Destino {
        dados.foto1Url?.let { foto1Url = it }
        dados.foto2Url?.let { foto2Url = it }
        dados.nome?.let { nome = it }
        dados.meta?.let{ meta = it}
        dados.descricao?.let{descricao = it}
        dados.preco?.let { preco = it }

        return this
    }

}