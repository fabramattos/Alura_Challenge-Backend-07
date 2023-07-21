package br.com.jornadamilhas.api.controller

import br.com.jornadamilhas.api.domain.depoimento.*
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

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class DepoimentoControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired @MockBean private val repository: DepoimentoRepository,
    @Autowired private val jacksonDtoCadastro: JacksonTester<DtoCadastroDepoimento>,
    @Autowired private val jacksonDtoDetalhamento: JacksonTester<DtoDetalhamentoDepoimento>,
    @Autowired private val jacksonDtoAtualizacao: JacksonTester<DtoAtualizacaoDepoimento>
) {

    private val DTO_CADASTRO_DEPOIMENTO = DtoCadastroDepoimento(
        "url.foto.com.br",
        "nome 1",
        "depoimento 1"
    )

    private val DTO_ATUALIZACAO_DEPOIMENTO = DtoAtualizacaoDepoimento(
        0L,
        "urlAtalizada.foto.com.br",
        "nome 1 atualizado",
        "depoimento 1 atualizado"
    )

    @Test
    @DisplayName("Dado um json válido, Quando tentar criar um depoimento, Deve retornar HTTP 201 e o DTO do depoimento")
    fun criar1() {
        val depoimentoCadastrado = Depoimento(DTO_CADASTRO_DEPOIMENTO)
        val jsonEsperado = jacksonDtoDetalhamento.write(DtoDetalhamentoDepoimento(depoimentoCadastrado)).json

        Mockito
            .`when`(repository.save(Mockito.any()))
            .thenReturn(depoimentoCadastrado)

        mockMvc
            .perform(
                post("/depoimentos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jacksonDtoCadastro.write(DTO_CADASTRO_DEPOIMENTO).json)
            )
            .andExpect(status().isCreated)
            .andExpect(content().json(jsonEsperado))
    }

    @Test
    @DisplayName("Dado um json inválido, Quando tentar criar um depoimento, Deve retornar HTTP 400")
    fun criar2() {
        mockMvc
            .perform(
                post("/depoimentos")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest)
    }

    @Test
    @DisplayName("Dado um id válido, Quando tentar exibir detalhes de um depoimento, Deve devolver HTTP 200")
    fun exibir1() {
        val depoimentoCriado = Depoimento(DTO_CADASTRO_DEPOIMENTO)

        Mockito
            .`when`(repository.getReferenceById(Mockito.anyLong()))
            .thenReturn(depoimentoCriado)

        val jsonEsperado = jacksonDtoDetalhamento
            .write(DtoDetalhamentoDepoimento(depoimentoCriado))
            .json

        mockMvc
            .perform(get("/depoimentos").param("id", "1"))
            .andExpect(status().isOk)
            .andExpect(content().json(jsonEsperado))

    }

    @Test
    @DisplayName("Dado um id inválido, Quando tentar exibir detalhes de um depoimento, Deve devolver HTTP 404")
    fun exibir2() {

        Mockito
            .`when`(repository.getReferenceById(Mockito.any()))
            .thenThrow(EntityNotFoundException())

        mockMvc
            .perform(get("/depoimentos").param("id", "1"))
            .andExpect(status().isNotFound)
    }

    @Test
    @DisplayName("Dado um JSON válido, Quando tentar atualizar um depoimento, Deve retornar HTTP 200 e o DTO do depoimento atualizado")
    fun atualizar1() {
        val depoimento = Depoimento(DTO_CADASTRO_DEPOIMENTO)

        Mockito
            .`when`(repository.getReferenceById(Mockito.any()))
            .thenReturn(depoimento)

        val jsonEsperado = jacksonDtoAtualizacao
            .write(DTO_ATUALIZACAO_DEPOIMENTO)
            .json

        mockMvc
            .perform(
                put("/depoimentos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jacksonDtoAtualizacao.write(DTO_ATUALIZACAO_DEPOIMENTO).json)
            )
            .andExpect(status().isOk)
            .andExpect(content().json(jsonEsperado))
    }

    @Test
    @DisplayName("Dado um JSON inválido, Quando tentar atualizar um depoimento, Deve retornar HTTP 400")
    fun atualizar2() {
        mockMvc
            .perform(
                put("/depoimentos")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest)
    }

    @Test
    @DisplayName("Dado um JSON válido com id inválido, Quando tentar atualizar um depoimento, Deve devolver HTTP 404")
    fun atualizar3() {
        Mockito
            .`when`(repository.getReferenceById(Mockito.any()))
            .thenThrow(EntityNotFoundException()) // simulando que o ID lançará erro de entidade não encontrada

        mockMvc
            .perform(
                put("/depoimentos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jacksonDtoAtualizacao.write(DTO_ATUALIZACAO_DEPOIMENTO).json)
            ) //json válido
            .andExpect(status().isNotFound)
    }

    @Test
    @DisplayName("Dado um id válido, Quando tentar excluir um depoimento, Deve retornar HTTP 204")
    fun delete1() {
        mockMvc
            .perform(delete("/depoimentos").param("id", "1"))
            .andExpect(status().isNoContent)
    }

    @Test
    @DisplayName("Dado um id inválido, Quando tentar excluir um depoimento, Deve retornar HTTP 404")
    fun delete2() {
        Mockito
            .`when`(repository.deleteById(Mockito.any()))
            .thenThrow(EntityNotFoundException())

        mockMvc
            .perform(delete("/depoimentos").param("id", "1"))
            .andExpect(status().isNotFound)
    }

    @Test
    @DisplayName("Deveria exibir codigo 200 ao acessar a home")
    fun exibeHome() {
        mockMvc
            .perform(get("/depoimentos-home"))
            .andExpect(status().isOk)
    }

}