package br.com.jornadamilhas.api.domain.destino

import br.com.jornadamilhas.domain.destino.Destino

object DestinoTest {

    fun validoBuild() = Destino(DtoCadastroDestinoTest.validoBuild())

}
