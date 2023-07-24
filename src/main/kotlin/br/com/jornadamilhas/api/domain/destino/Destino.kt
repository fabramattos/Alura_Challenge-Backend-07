package br.com.jornadamilhas.api.domain.destino

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.EqualsAndHashCode
import lombok.Getter
import lombok.NoArgsConstructor
import java.math.BigDecimal

@Table(name = "destinos")
@Entity(name = "Destino")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = ["id"])
class Destino(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long,
    var foto: String,
    var nome: String,
    var preco: BigDecimal)
{

    constructor(dados : DtoCadastroDestino) : this(0L, dados.fotoUrl, dados.nome, dados.preco)

    fun atualiza(dados : DtoAtualizacaoDestino){
        dados.fotoUrl?.let { foto = it }
        dados.nome?.let { nome = it }
        dados.preco?.let { preco = it }
    }

}