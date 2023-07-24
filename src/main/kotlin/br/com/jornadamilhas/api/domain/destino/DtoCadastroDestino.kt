package br.com.jornadamilhas.api.domain.destino

import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.URL
import java.math.BigDecimal

data class DtoCadastroDestino(
    @NotBlank
    val id: Long = 0L,
    @NotBlank @URL
    val fotoUrl: String,
    @NotBlank
    val nome: String,
    @NotBlank
    val preco: BigDecimal
)
