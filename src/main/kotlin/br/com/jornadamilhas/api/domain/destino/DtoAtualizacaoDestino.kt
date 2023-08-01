package br.com.jornadamilhas.api.domain.destino

import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.URL
import org.jetbrains.annotations.NotNull
import java.math.BigDecimal

data class DtoAtualizacaoDestino(
    @field: NotNull
    val id: Long,

    @field: URL
    val foto1Url: String? = null,

    @field: URL
    val foto2Url: String? = null,

    val nome:String? = null,

    @field: Size(max = 160)
    val meta: String? = null,

    val descricao: String? = null,
    val preco: BigDecimal? = null)