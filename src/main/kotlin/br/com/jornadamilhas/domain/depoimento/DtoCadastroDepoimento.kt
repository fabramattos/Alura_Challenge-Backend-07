package br.com.jornadamilhas.domain.depoimento

import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.URL

data class DtoCadastroDepoimento(
    @field: NotBlank @field: URL
    val fotoUrl: String,

    @field: NotBlank
    val nome:String,

    @field: NotBlank
    val depoimento: String) {
}