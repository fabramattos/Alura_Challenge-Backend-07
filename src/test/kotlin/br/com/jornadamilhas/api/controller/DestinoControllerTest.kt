package br.com.jornadamilhas.api.controller

import br.com.jornadamilhas.api.domain.destino.*
import br.com.jornadamilhas.api.integration.DatabaseContainerConfiguration
import br.com.jornadamilhas.domain.destino.DestinoRepository
import br.com.jornadamilhas.domain.destino.DtoAtualizacaoDestino
import br.com.jornadamilhas.domain.destino.DtoCadastroDestino
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.json.JacksonTester
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.*
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@WithMockUser(authorities = ["ADMIN"])
class DestinoControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val jacksonDtoCadastro: JacksonTester<DtoCadastroDestino>,
    @Autowired private val jacksonDtoAtualizacao: JacksonTester<DtoAtualizacaoDestino>,
) : DatabaseContainerConfiguration() {

    @Autowired
    private lateinit var repository: DestinoRepository
    private val URI = "/destinos"
    private val DTO_CADASTRO_DESTINO = DtoCadastroDestinoTest.validoBuild()
    private val DESTINO = DestinoTest.validoBuild()

    @Test
    @DisplayName("Dado um json válido, Quando enviar um Post, Então deveria retornar 201")
    fun criar1() {
        mockMvc
            .post(URI) {
                contentType = MediaType.APPLICATION_JSON
                content = jacksonDtoCadastro.write(DTO_CADASTRO_DESTINO).json
            }
            .andExpect { status { isCreated() } }
    }


    @Test
    @DisplayName("Dado no campo 'busca' um nome existente, Quando solicitado via GET, Então deveria retornar 200")
    fun exibir1() {
        val destino = salvaDestino()

        mockMvc
            .get(URI) {
                param("nome", destino.nome)
            }
            .andExpect { status { isOk() } }
    }

    @Test
    @DisplayName("Dado no campo 'busca' um nome inexistente, Quando solicitado via GET, Então deveria retornar 400")
    fun exibir2() {
        mockMvc
            .get(URI) {
                param("nome", "Xablau")
            }
            .andExpect { status { isBadRequest() } }
    }

    @Test
    @DisplayName("Dado na url um id existente, Quando solicitado via GET, Então deveria retornar codigo 200")
    fun exibir3() {
        val destino = salvaDestino()

        mockMvc
            .get(URI.plus("/${destino.id}"))
            .andExpect { status { isOk() } }
    }


    @Test
    @DisplayName("Dado na url um id inexistente, Quando solicitado via GET, Então deveria retornar 400")
    fun exibir4() {
        mockMvc
            .get(URI.plus("/-1"))
            .andExpect { status { isBadRequest() } }
    }

    @Test
    @DisplayName("Dado um JSON válido, Quando tentar atualizar um destino, Deve retornar HTTP 200")
    fun atualiza1() {
        val destino = salvaDestino()
        val dtoAtualizacao = DtoAtualizacaoDestinoTest.build(destino.id!!)

        mockMvc
            .put(URI){
                contentType = MediaType.APPLICATION_JSON
                content = jacksonDtoAtualizacao.write(dtoAtualizacao).json
            }
            .andExpect { status { isOk() } }
    }

    @Test
    @DisplayName("Dado um JSON inválido, Quando tentar atualizar um destino, Deve retornar HTTP 400")
    fun atualiza2() {
        mockMvc
            .put(URI){contentType = MediaType.APPLICATION_JSON}
            .andExpect { status { isBadRequest() } }
    }

    @Test
    @DisplayName("Dado um JSON válido com id inválido, Quando tentar atualizar um destino, Deve devolver HTTP 400")
    fun atualiza3() {
        salvaDestino()
        val dtoAtualizacao = DtoAtualizacaoDestinoTest.build(-1)
        mockMvc
            .put(URI){
                contentType = MediaType.APPLICATION_JSON
                content = jacksonDtoAtualizacao.write(dtoAtualizacao).json
            }
            .andExpect { status { isBadRequest() } }
    }

    @Test
    @DisplayName("Dado um id válido, Quando tentar excluir um destino, Deve retornar HTTP 204")
    fun delete1() {
        val destino = salvaDestino()

        mockMvc
            .delete(URI){
                param("id", destino.id.toString())
            }
            .andExpect { status { isNoContent() } }
    }

    @Test
    @DisplayName("Dado um id inválido, Quando tentar excluir um destino, Deve retornar 400")
    fun delete2() {
        mockMvc
            .delete(URI){
                param("id", "-1")
            }
            .andExpect { status { isBadRequest() } }
    }

    private fun salvaDestino() = repository.save(DESTINO)

}