package br.com.jornadamilhas.api.controller

import br.com.jornadamilhas.api.domain.destino.*
import jakarta.persistence.EntityNotFoundException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.json.JacksonTester
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.math.BigDecimal

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class DestinoControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired @MockBean private val repository: DestinoRepository,
    @Autowired private val jacksonDtoCadastro: JacksonTester<DtoCadastroDestino>,
    @Autowired private val jacksonDtoDetalhamento: JacksonTester<DtoDetalhamentoDestino>,
    @Autowired private val jacksonListDtoDetalhamento: JacksonTester<List<DtoDetalhamentoDestino>>,
    @Autowired private val jacksonDtoAtualizacao: JacksonTester<DtoAtualizacaoDestino>
){

    private val DTO_CADASTRO_DESTINO = DtoCadastroDestino("urlFoto", "Brasil", BigDecimal("300.00"))
    private val DTO_ATUALIZACAO_DESTINO = DtoAtualizacaoDestino(1L, null, null, BigDecimal("999.99"))

    @Test
    @DisplayName("Dado um json válido, Quando enviar um Post, Então deveria retornar 201 e o json detalhado")
    fun criar1() {

        val destino = Destino(DTO_CADASTRO_DESTINO)
        val jsonEsperado = jacksonDtoDetalhamento.write(DtoDetalhamentoDestino(destino)).json

        mockMvc
            .perform(
                post("/destinos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jacksonDtoCadastro.write(DTO_CADASTRO_DESTINO).json))
            .andExpect(status().isCreated)
            .andExpect(content().json(jsonEsperado))
    }

    @Test
    @DisplayName("Dado um nome existente no danco de dados, Quando solicitado via GET, Então deveria retornar todos resultados")
    fun exibir1() {

        val destinos = listaDestinosBrasil()
        val dtoDestinos = destinos.map { destino -> DtoDetalhamentoDestino(destino) }
        val jsonDestinos = jacksonListDtoDetalhamento.write(dtoDestinos).json

        Mockito
            .`when`(repository.findAllByNome(Mockito.anyString()))
            .thenReturn(destinos)

        mockMvc
            .perform(
                get("/destinos")
                    .param("nome", "Brasil"))
            .andExpect(status().isOk)
            .andExpect(content().json(jsonDestinos))
    }

    @Test
    @DisplayName("Dado um nome inexistente no danco de dados, Quando solicitado via GET, Então deveria retornar 400")
    fun exibir2() {
        mockMvc
            .perform(
                get("/destinos")
                    .param("nome", "Brasil"))
            .andExpect(status().isBadRequest)
    }

    @Test
    @DisplayName("Dado um JSON válido, Quando tentar atualizar um destino, Deve retornar HTTP 200 e o DTO do destino atualizado")
    fun atualiza1() {
        val destino = Destino(DTO_CADASTRO_DESTINO)

        Mockito
            .`when`(repository.getReferenceById(Mockito.anyLong()))
            .thenReturn(destino)

        val dtoDetalhamento = DtoDetalhamentoDestino(destino.id, destino.foto, destino.nome, BigDecimal("999.99"))
        val jsonEsperado = jacksonDtoDetalhamento.write(dtoDetalhamento).json

        mockMvc
            .perform(put("/destinos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonDtoAtualizacao.write(DTO_ATUALIZACAO_DESTINO).json))
            .andExpect(status().isOk)
            .andExpect(content().json(jsonEsperado))
    }

    @Test
    @DisplayName("Dado um JSON inválido, Quando tentar atualizar um destino, Deve retornar HTTP 400")
    fun atualiza2() {
        mockMvc
            .perform(
                put("/destinos")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest)
    }

    @Test
    @DisplayName("Dado um JSON válido com id inválido, Quando tentar atualizar um destino, Deve devolver HTTP 404")
    fun atualiza3() {
        Mockito
            .`when`(repository.getReferenceById(Mockito.anyLong()))
            .thenThrow(EntityNotFoundException()) // simulando que o ID lançará erro de entidade não encontrada

        mockMvc
            .perform(
                put("/destinos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jacksonDtoAtualizacao.write(DTO_ATUALIZACAO_DESTINO).json)) //json válido
            .andExpect(status().isNotFound)
    }

    @Test
    @DisplayName("Dado um id válido, Quando tentar excluir um destino, Deve retornar HTTP 204")
    fun delete1() {
        mockMvc
            .perform(delete("/destinos").param("id", "1"))
            .andExpect(status().isNoContent)
    }

    @Test
    @DisplayName("Dado um id inválido, Quando tentar excluir um destino, Deve retornar HTTP 404")
    fun delete2() {
        Mockito
            .`when`(repository.deleteById(Mockito.any()))
            .thenThrow(EntityNotFoundException())

        mockMvc
            .perform(delete("/destinos").param("id", "1"))
            .andExpect(status().isNotFound)
    }

    fun listaDestinosBrasil() = listOf<Destino>(
        Destino(DtoCadastroDestino("fotoUrl.com.br", "Brasil", BigDecimal("500.00"))),
        Destino(DtoCadastroDestino("fotoUrl.com.br", "Brasil", BigDecimal("600.00"))),
        Destino(DtoCadastroDestino("fotoUrl.com.br", "Brasil", BigDecimal("700.00"))),
        Destino(DtoCadastroDestino("fotoUrl.com.br", "Brasil", BigDecimal("800.00"))),
        Destino(DtoCadastroDestino("fotoUrl.com.br", "Brasil", BigDecimal("900.00")))
    )
}