package br.com.jornadamilhas.api.domain.destino

import org.hibernate.validator.constraints.URL
import org.jetbrains.annotations.NotNull
import java.math.BigDecimal

data class DtoAtualizacaoDestino(
    @NotNull
    val id: Long,
    @URL
    val fotoUrl: String?,
    val nome:String?,
    val preco: BigDecimal?){
}
