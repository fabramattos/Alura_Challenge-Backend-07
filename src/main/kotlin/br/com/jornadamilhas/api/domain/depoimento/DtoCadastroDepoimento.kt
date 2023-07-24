package br.com.jornadamilhas.api.domain.depoimento

import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.URL

data class DtoCadastroDepoimento(
    @NotBlank @URL
    val fotoUrl: String,
    @NotBlank
    val nome:String,
    @NotBlank
    val depoimento: String) {
}