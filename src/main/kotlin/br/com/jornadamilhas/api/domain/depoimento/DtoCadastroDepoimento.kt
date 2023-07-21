package br.com.jornadamilhas.api.domain.depoimento

import jakarta.validation.constraints.NotBlank

data class DtoCadastroDepoimento(
    @NotBlank
    val fotoUrl: String,
    @NotBlank
    val nome:String,
    @NotBlank
    val depoimento: String) {
}