package br.com.jornadamilhas.domain.depoimento

import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.URL
import org.jetbrains.annotations.NotNull

data class DtoAtualizacaoDepoimento(
    @field: NotNull
    val id: Long,

    @field: URL
    val fotoUrl: String?,

    val nome:String?,

    @field: NotBlank
    val depoimento: String){
}
