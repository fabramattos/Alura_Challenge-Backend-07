package br.com.jornadamilhas.api.domain.destino

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.URL
import java.math.BigDecimal

data class DtoCadastroDestino(
    @field: NotBlank @field: URL
    val foto1Url: String,

    @field: NotBlank @field: URL
    val foto2Url: String,

    @field: NotBlank
    val nome: String,

    @field: NotBlank @field: Size(max = 160)
    val meta: String,

    var descricao: String?,

    @field: NotNull
    val preco: BigDecimal
)