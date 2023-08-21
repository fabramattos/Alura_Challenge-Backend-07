package br.com.jornadamilhas.api.domain.depoimento

import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.URL
import org.jetbrains.annotations.NotNull

data class DtoAtualizacaoDepoimento(
    @field: NotNull
    var id: Long,

    @field: URL
    val fotoUrl: String?,

    val nome:String?,

    @field: NotBlank
    val depoimento: String){
}
