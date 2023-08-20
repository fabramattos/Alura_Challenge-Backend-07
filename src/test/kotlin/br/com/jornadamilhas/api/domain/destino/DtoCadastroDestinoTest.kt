package br.com.jornadamilhas.api.domain.destino

import java.math.BigDecimal

object DtoCadastroDestinoTest {

    fun validoBuild() = DtoCadastroDestino(
        foto1Url = "http://www.foto1.com.br",
        foto2Url = "http://www.foto2.com.br",
        nome = "nome",
        meta = "meta",
        descricao = "descricao",
        preco = BigDecimal("100.00")
    )

    fun semDescricaoBuild() = DtoCadastroDestino(
        foto1Url = "http://www.foto1.com.br",
        foto2Url = "http://www.foto2.com.br",
        nome = "nome",
        meta = "meta",
        descricao = null,
        preco = BigDecimal("100.00")
    )
}