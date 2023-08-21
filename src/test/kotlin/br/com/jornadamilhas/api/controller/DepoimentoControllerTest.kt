package br.com.jornadamilhas.api.controller

import br.com.jornadamilhas.api.domain.depoimento.*
import br.com.jornadamilhas.api.infra.exception.DtoException
import br.com.jornadamilhas.api.integration.DatabaseContainerConfiguration
import jakarta.transaction.Transactional
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.json.JacksonTester
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@Transactional
@WithMockUser(authorities = ["USER"])
class DepoimentoControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val jacksonDtoCadastro: JacksonTester<DtoCadastroDepoimento>,
    @Autowired private val jacksonDtoAtualizacao: JacksonTester<DtoAtualizacaoDepoimento>,
) : DatabaseContainerConfiguration() {

    @Autowired
    private lateinit var repository: DepoimentoRepository

    private final val URI = "/depoimentos"
    private final val DTO_CADASTRO = DtoCadastroDepoimentoTest.build()
    private final val DEPOIMENTO = Depoimento(DTO_CADASTRO)
    private final val DTO_ATUALIZACAO = DtoAtualizacaoDepoimentoTest.build()


    @BeforeEach
    fun setup() {
    }

    @Test
    @DisplayName("Dado um json válido, Quando tentar criar um depoimento, Deve retornar HTTP 201 e o DTO do depoimento")
    fun criar1() {
        salvaDepoimento()
            .andExpect {
                status { isCreated() }
            }
    }

    @Test
    @DisplayName("Dado um json inválido, Quando tentar criar um depoimento, Deve retornar HTTP 400")
    fun criar2() {
        mockMvc
            .post(URI)
            .andExpect { status { isBadRequest() } }
    }


    @Test
    @DisplayName("Dado um id válido, Quando tentar exibir detalhes de um depoimento, Deve devolver HTTP 200")
    fun exibir1() {
        salvaDepoimento()

        mockMvc
            .get(URI) { param("id", buscaIdValido().toString()) }
            .andExpect { status { isOk() } }
    }

    @Test
    @DisplayName("Dado um id inválido, Quando tentar exibir detalhes de um depoimento, Deve devolver HTTP 400")
    fun exibir2() {
        mockMvc
            .get(URI) { param("id", "111") }
            .andExpect { status { isBadRequest() } }
    }

    @Test
    @DisplayName("Dado um JSON válido, Quando tentar atualizar um depoimento, Deve retornar HTTP 200")
    fun atualizar1() {
        salvaDepoimento()

        buscaIdValido()?.let { DTO_ATUALIZACAO.id = it }

        mockMvc
            .put(URI) {
                contentType = MediaType.APPLICATION_JSON
                content = jacksonDtoAtualizacao.write(DTO_ATUALIZACAO).json
            }
            .andExpect { status { isOk() } }
    }

    @Test
    @DisplayName("Dado um JSON inválido, Quando tentar atualizar um depoimento, Deve retornar HTTP 400")
    fun atualizar2() {
        mockMvc
            .put(URI) { contentType = MediaType.APPLICATION_JSON }
            .andExpect { status().isBadRequest }
    }

    @Test
    @DisplayName("Dado um JSON válido com id inválido, Quando tentar atualizar um depoimento, Deve devolver HTTP 404")
    fun atualizar3() {
        DTO_ATUALIZACAO.id = 9999

        mockMvc
            .put(URI) {
                contentType = MediaType.APPLICATION_JSON
                content = jacksonDtoAtualizacao.write(DTO_ATUALIZACAO).json
            }
            .andExpect { status { isBadRequest() } }
    }

    @Test
    @DisplayName("Dado um id válido, Quando tentar excluir um depoimento, Deve retornar HTTP 204")
    fun delete1() {
        salvaDepoimento()

        mockMvc
            .delete(URI){
                param("id", buscaIdValido().toString())
            }
            .andExpect { status { isNoContent() } }
    }

    @Test
    @DisplayName("Dado um id inválido, Quando tentar excluir um depoimento, Deve retornar HTTP 400")
    fun delete2() {
        mockMvc
            .delete(URI){
                param("id", "9999")
            }
            .andExpect { status { isBadRequest() } }
    }

    @Test
    @DisplayName("Deveria exibir codigo 200 ao acessar a home")
    fun exibeHome() {
        salvaDepoimento()

        mockMvc
            .get(URI.plus("/home"))
            .andExpect { status { isOk() } }
    }

    private fun salvaDepoimento() = mockMvc
        .post(URI) {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonDtoCadastro.write(DTO_CADASTRO).json
        }

    private fun buscaIdValido(): Long? = repository.findAll().get(0).id
}