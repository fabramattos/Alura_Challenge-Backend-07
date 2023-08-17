package br.com.jornadamilhas.api.domain.depoimento

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Table(name = "depoimentos")
@Entity(name = "Depoimento")
class Depoimento(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
    @NotNull
    var nome : String,
    @NotNull
    var foto: String,
    @NotNull
    var depoimento: String
) {
    fun atualizarInformacoes(dados: DtoAtualizacaoDepoimento): Depoimento {
        dados.fotoUrl?.let { foto = it }
        dados.nome?.let { nome = it }
        depoimento = dados.depoimento

        return this
    }

    constructor(dados : DtoCadastroDepoimento) :
            this(nome = dados.nome, foto = dados.fotoUrl, depoimento = dados.depoimento) {
    }


}