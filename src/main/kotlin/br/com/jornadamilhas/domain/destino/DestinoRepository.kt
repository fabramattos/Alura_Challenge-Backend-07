package br.com.jornadamilhas.domain.destino

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional


interface DestinoRepository : JpaRepository<Destino, Long> {

    fun findAllByNome(nome: String): Optional<List<Destino>>

}