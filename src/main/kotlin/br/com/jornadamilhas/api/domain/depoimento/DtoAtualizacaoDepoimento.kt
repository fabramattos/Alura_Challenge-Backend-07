package br.com.jornadamilhas.api.domain.depoimento

import jakarta.validation.constraints.NotBlank
import org.jetbrains.annotations.NotNull

data class DtoAtualizacaoDepoimento(
    @NotNull
    val id: Long,
    val fotoUrl: String?,
    val nome:String?,
    @NotBlank
    val depoimento: String){
}
