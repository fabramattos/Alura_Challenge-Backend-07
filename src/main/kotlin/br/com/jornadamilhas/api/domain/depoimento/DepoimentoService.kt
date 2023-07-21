package br.com.jornadamilhas.api.domain.depoimento

import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class DepoimentoService(private val repository: DepoimentoRepository) {

    fun escolheTresAleatorios() : List<Depoimento> {
        val depoimentos = repository.findAll()
        return getDepoimentosAleatoriosUnicos(3, depoimentos)
    }

    private fun getDepoimentosAleatoriosUnicos(qtdeDesejada: Int, depoimentos: List<Depoimento>) : List<Depoimento> {
        if(qtdeDesejada >= depoimentos.size)
            return depoimentos

        val random = Random(System.currentTimeMillis())
        val setAleatorio = mutableSetOf<Depoimento>()

        while (setAleatorio.size < qtdeDesejada){
            setAleatorio.add(depoimentos[random.nextInt(depoimentos.size)])
        }

        return setAleatorio.toList()

    }

}