package br.com.jornadamilhas.api.service

import org.springframework.stereotype.Component

@Component
class GeradorDescricao {
    fun geraDescricao(): String {
        var textoLorem = ""
        for(i in 1..30) {
            textoLorem += "Lorem Ipsum "
        }
        textoLorem = textoLorem.substring(0,95) + "..."

        var textoFinal = textoLorem
        textoFinal+= System.lineSeparator() + System.lineSeparator()
        textoFinal+= textoLorem

        return textoFinal
    }

}
