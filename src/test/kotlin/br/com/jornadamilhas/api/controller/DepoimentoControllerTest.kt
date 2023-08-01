package br.com.jornadamilhas.api.controller

import br.com.jornadamilhas.api.domain.depoimento.*
import br.com.jornadamilhas.api.infra.exception.DepoimentoNaoEncontradoException
import br.com.jornadamilhas.api.infra.exception.DtoException
import jakarta.persistence.EntityNotFoundException
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class DepoimentoControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired @MockBean private val repository: DepoimentoRepository,
    @Autowired private val jacksonDtoCadastro: JacksonTester<DtoCadastroDepoimento>,
    @Autowired private val jacksonDtoDetalhamento: JacksonTester<DtoDetalhamentoDepoimento>,
    @Autowired private val jacksonDtoAtualizacao: JacksonTester<DtoAtualizacaoDepoimento>,
    @Autowired private val jacksonDtoExcetion: JacksonTester<DtoException>,
) {

    private final val DTO_EXEPTION = DtoException("Nenhum depoimento encontrado!")

    private final val DTO_CADASTRO_DEPOIMENTO = DtoCadastroDepoimento(
        "http://url.foto.com.br",
        "nome 1",
        "depoimento 1"
    )

    private final val DTO_ATUALIZACAO_DEPOIMENTO = DtoAtualizacaoDepoimento(
        0L,
        "http://urlAtalizada.foto.com.br",
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
        val depoimento = Depoimento(DTO_CADASTRO_DEPOIMENTO)

        Mockito
            .`when`(repository.findById(Mockito.anyLong()))
            .thenReturn(Optional.of(depoimento))

        val jsonEsperado = jacksonDtoDetalhamento
            .write(DtoDetalhamentoDepoimento(depoimento))
            .json

        mockMvc
            .perform(get("/depoimentos").param("id", "1"))
            .andExpect(status().isOk)
            .andExpect(content().json(jsonEsperado))

    }

    @Test
    @DisplayName("Dado um id inválido, Quando tentar exibir detalhes de um depoimento, Deve devolver HTTP 400")
    fun exibir2() {
        val jsonException = jacksonDtoExcetion.write(DTO_EXEPTION).json

        Mockito
            .`when`(repository.getReferenceById(Mockito.any()))
            .thenThrow(EntityNotFoundException())

        mockMvc
            .perform(get("/depoimentos").param("id", "1"))
            .andExpect(status().isBadRequest)
            .andExpect(content().json(jsonException))
    }

    @Test
    @DisplayName("Dado um JSON válido, Quando tentar atualizar um depoimento, Deve retornar HTTP 200 e o DTO do depoimento atualizado")
    fun atualizar1() {
        val depoimento = Depoimento(DTO_CADASTRO_DEPOIMENTO)

        Mockito
            .`when`(repository.findById(Mockito.any()))
            .thenReturn(Optional.of(depoimento))

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
            .`when`(repository.findById(Mockito.anyLong()))
            .thenThrow(DepoimentoNaoEncontradoException())

        val jsonEsperado = jacksonDtoExcetion.write(DTO_EXEPTION).json

        mockMvc
            .perform(
                put("/depoimentos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jacksonDtoAtualizacao.write(DTO_ATUALIZACAO_DEPOIMENTO).json) // json válido
            )
            .andExpect(status().isBadRequest)
            .andExpect(content().json(jsonEsperado)) //id invalido
    }

    @Test
    @DisplayName("Dado um id válido, Quando tentar excluir um depoimento, Deve retornar HTTP 204")
    fun delete1() {
        val depoimento = Depoimento(DTO_CADASTRO_DEPOIMENTO)

        Mockito
            .`when`(repository.findById(Mockito.anyLong()))
            .thenReturn(Optional.of(depoimento))

        Mockito
            .doNothing()
            .`when`(repository).delete(Mockito.any())

        mockMvc
            .perform(delete("/depoimentos").param("id", "1"))
            .andExpect(status().isNoContent)
    }

    @Test
    @DisplayName("Dado um id inválido, Quando tentar excluir um depoimento, Deve retornar HTTP 400")
    fun delete2() {
        val jsonEsperado = jacksonDtoExcetion.write(DTO_EXEPTION).json

        mockMvc
            .perform(delete("/depoimentos").param("id", "1"))
            .andExpect(status().isBadRequest)
            .andExpect(content().json(jsonEsperado))
    }

    @Test
    @DisplayName("Deveria exibir codigo 200 ao acessar a home")
    fun exibeHome() {
        mockMvc
            .perform(get("/depoimentos-home"))
            .andExpect(status().isOk)
    }

}