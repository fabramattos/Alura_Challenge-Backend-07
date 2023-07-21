package br.com.jornadamilhas.api.domain.depoimento

import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DepoimentoServiceTest(
    @Autowired val repository: DepoimentoRepository,
    @Autowired val em : EntityManager)
{

    private val service = DepoimentoService(repository)

    @Test
    @DisplayName("Dado um BD com menos de 3 depoimentos, Quando solicitado 3 aleatorios, Deve retornar o total disponivel")
    fun escolheTresAleatoriosCenario1() {
        criaDepoimentos(2)
        val listaAleatoria = service.escolheTresAleatorios()

        assertEquals(2, listaAleatoria.size)
    }

    @Test
    @DisplayName("Dado um BD com mais de 3 depoimentos, Quando solicitado 3 aleatorios, Deve retornar 3 aleatorios")
    fun escolheTresAleatoriosCenario2() {
        criaDepoimentos(50)
        val listaAleatoria1 = service.escolheTresAleatorios()
        val listaAleatoria2 = service.escolheTresAleatorios()

        assertEquals(3, listaAleatoria1.size)
        assertEquals(3, listaAleatoria2.size)

        assertFalse(listaAleatoria1.containsAll(listaAleatoria2), "Deveriam ter conteudos diferentes")
    }

    private fun criaDepoimentos(qtdeDepoimentos : Int){
        for (i in 0 until qtdeDepoimentos){
            val depoimento = Depoimento(
                0L,
                "nome $i",
                "url foto $i",
                "depoimento $i")

            em.persist(depoimento)
        }
    }
}