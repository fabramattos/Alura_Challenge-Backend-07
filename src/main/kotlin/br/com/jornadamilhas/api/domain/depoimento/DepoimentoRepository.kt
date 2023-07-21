package br.com.jornadamilhas.api.domain.depoimento

import org.springframework.data.jpa.repository.JpaRepository

interface DepoimentoRepository : JpaRepository<Depoimento, Long> {
}