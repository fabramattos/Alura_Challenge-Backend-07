package br.com.jornadamilhas.api.domain.destino

import org.springframework.data.jpa.repository.JpaRepository

interface DestinoRepository : JpaRepository <Destino, Long> {

    fun findAllByNome(nome : String) : List<Destino>
}